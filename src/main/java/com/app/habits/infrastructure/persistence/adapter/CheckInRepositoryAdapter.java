package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.model.TodayHabitStatus;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.infrastructure.persistence.mapper.CheckInEntityMapper;
import com.app.habits.infrastructure.persistence.spring.CheckInJpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckInRepositoryAdapter implements CheckInRepositoryPort {

    private final CheckInJpaRepository repo;

    public CheckInRepositoryAdapter(CheckInJpaRepository repo) {
        this.repo = repo;
    }

    private record DayCountImpl(LocalDate day, long count) implements DayCount {}
    private record IdCountImpl(String id, long count) implements IdCount {}

    @Override
    public boolean existsByHabitAndDay(String habitId, LocalDate day) {
        return repo.existsByHabitIdAndCheckedOn(habitId, day);
    }

    @Override
    public CheckIn save(CheckIn checkIn) {
        var saved = repo.save(CheckInEntityMapper.toEntity(checkIn));
        return CheckInEntityMapper.toDomain(saved);
    }

    @Override
    public int deleteByHabitAndDay(String habitId, LocalDate day) {
        return repo.deleteByHabitIdAndCheckedOn(habitId, day);
    }

    @Override
    public List<TodayHabitStatus> listTodayStatusByUser(String userId, LocalDate day) {
        return repo.listTodayStatus(userId, day)
                .stream()
                .map(r -> CheckInEntityMapper.toTodayStatus(r.getHabitId(), r.getName(),
                        Boolean.TRUE.equals(r.getDone()), r.getCheckedAt()))
                .toList();
    }

    @Override
    public List<DayCount> countByUserGroupedPerDay(String userId, LocalDate start, LocalDate end) {
        return repo.countPerDay(userId, start, end).stream()
                .map(r -> (DayCount) new DayCountImpl(r.getDay(), r.getCount()))
                .toList();
    }

    @Override
    public List<IdCount> countByUserGroupedPerHabit(String userId, LocalDate start, LocalDate end) {
        return repo.countPerHabit(userId, start, end).stream()
                .map(r -> (IdCount) new IdCountImpl(r.getId(), r.getCount()))
                .toList();
    }

    @Override
    public List<IdCount> countByUserGroupedPerCategory(String userId, LocalDate start, LocalDate end) {
        return repo.countPerCategory(userId, start, end).stream()
                .map(r -> (IdCount) new IdCountImpl(r.getId(), r.getCount()))
                .toList();
    }

    @Override
    public List<LocalDate> listCheckedDaysByHabit(String habitId) {
        return repo.listCheckedDaysByHabit((habitId));
    }

    @Override
    public Map<LocalDate, Long> countDistinctHabitsPerDay(String userId, LocalDate start, LocalDate end) {
        var rows = repo.countDistinctHabitsPerDay(userId, start, end);
        Map<LocalDate, Long> m = new LinkedHashMap<>();
        for (Object[] r : rows) {
            m.put(((Date) r[0]).toLocalDate(), ((Number) r[1]).longValue());
        }
        return m;
    }

    @Override
    public Optional<LocalDate> findFirstCheckInDateByUser(String userId) {
        return Optional.ofNullable(repo.findFirstCheckInDateByUser(userId));
    }
}
