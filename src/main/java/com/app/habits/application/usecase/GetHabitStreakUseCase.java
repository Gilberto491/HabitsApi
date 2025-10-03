package com.app.habits.application.usecase;

import com.app.habits.domain.port.CheckInRepositoryPort;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class GetHabitStreakUseCase {
    private final CheckInRepositoryPort checkIns;
    private final Clock clock;

    public record Result(int currentStreak, int maxStreak) {}

    public Result execute(String habitId) {
        var days = checkIns.listCheckedDaysByHabit(habitId);
        if (days.isEmpty()) return new Result(0, 0);

        int max = 1, cur = 1;
        for (int i = 1; i < days.size(); i++) {
            if (days.get(i).equals(days.get(i - 1).plusDays(1))) {
                cur++;
            } else {
                max = Math.max(max, cur);
                cur = 1;
            }
        }
        max = Math.max(max, cur);

        var today = LocalDate.now(clock);
        int current = 0;
        LocalDate d = today;

        Set<LocalDate> set = new HashSet<>(days);
        while (set.contains(d)) {
            current++;
            d = d.minusDays(1);
        }
        return new Result(current, max);
    }
}