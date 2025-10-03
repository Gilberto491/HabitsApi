package com.app.habits.application.usecase;

import com.app.habits.domain.model.AuthenticatedUser;
import com.app.habits.domain.model.Category;
import com.app.habits.domain.model.Habit;
import com.app.habits.domain.port.CategoryRepositoryPort;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.rest.dto.weekly.CategoryRowDto;
import com.app.habits.infrastructure.rest.dto.weekly.DayBarDto;
import com.app.habits.infrastructure.rest.dto.weekly.HabitProgressDto;
import com.app.habits.infrastructure.rest.dto.weekly.WeeklyOverviewDto;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GetCurrentWeekOverviewUseCase {

    private final HabitRepositoryPort habitRepo;
    private final CheckInRepositoryPort checkInRepo;
    private final Clock clock;
    private final CategoryRepositoryPort categoryRepo;

    public GetCurrentWeekOverviewUseCase(
            HabitRepositoryPort habitRepo,
            CheckInRepositoryPort checkInRepo,
            Clock clock, CategoryRepositoryPort categoryRepo) {
        this.habitRepo = habitRepo;
        this.checkInRepo = checkInRepo;
        this.clock = clock;
        this.categoryRepo = categoryRepo;
    }

    public WeeklyOverviewDto execute(AuthenticatedUser user) {
        LocalDate today = LocalDate.now(clock);
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

        Map<LocalDate, Long> perDay = checkInRepo.countByUserGroupedPerDay(user.id(), weekStart, weekEnd)
                .stream().collect(Collectors.toMap(CheckInRepositoryPort.DayCount::day, CheckInRepositoryPort.DayCount::count));

        List<DayBarDto> bars = weekDates(weekStart, weekEnd).stream()
                .map(d -> new DayBarDto(d, perDay.getOrDefault(d, 0L)))
                .toList();

        List<Habit> habits = habitRepo.findActiveByUser(user.id());
        Map<String, Long> doneByHabit = checkInRepo.countByUserGroupedPerHabit(user.id(), weekStart, weekEnd)
                .stream().collect(Collectors.toMap(CheckInRepositoryPort.IdCount::id, CheckInRepositoryPort.IdCount::count));

        List<HabitProgressDto> progress = habits.stream()
                .map(h -> {
                    long done = doneByHabit.getOrDefault(h.getId(), 0L);
                    int target = Math.max(1, h.getTargetPerWeek());
                    int pct = (int) Math.min(100, Math.round((done * 100.0) / target));
                    return new HabitProgressDto(
                            h.getId(), h.getName(), target, done, pct
                    );
                }).toList();

        Map<String, Long> doneByCategory = checkInRepo.countByUserGroupedPerCategory(user.id(), weekStart, weekEnd)
                .stream().collect(Collectors.toMap(CheckInRepositoryPort.IdCount::id, CheckInRepositoryPort.IdCount::count));

        Set<String> catIds = doneByCategory.keySet();

        Map<String, String> catNames = categoryRepo.findByIds(catIds).stream()
                .collect(Collectors.toMap(Category::id, Category::name));

        List<CategoryRowDto> categories = doneByCategory.entrySet().stream()
                .map(e -> new CategoryRowDto(
                        e.getKey(),
                        catNames.getOrDefault(e.getKey(), "Categoria"),
                        e.getValue()
                ))
                .toList();

        return new WeeklyOverviewDto(weekStart, weekEnd, bars, progress, categories);
    }

    private static List<LocalDate> weekDates(LocalDate start, LocalDate end) {
        List<LocalDate> days = new ArrayList<>(7);
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) days.add(d);
        return days;
    }
}
