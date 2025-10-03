package com.app.habits.application.usecase;

import com.app.habits.domain.port.CheckInRepositoryPort;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;

@AllArgsConstructor
public class GetOverallStreakUseCase {
    private final CheckInRepositoryPort checkIns;
    private final Clock clock;

    public record Result(int currentStreak, int maxStreak, int minPerDay) {}

    public Result execute(String userId, int minPerDay) {
        var today = LocalDate.now(clock);
        var start = checkIns.findFirstCheckInDateByUser(userId).orElse(today);
        var counts = checkIns.countDistinctHabitsPerDay(userId, start, today); // ordenado

        if (counts.isEmpty()) return new Result(0, 0, minPerDay);

        int max = 0, cur = 0;
        LocalDate last = null;

        for (var entry : counts.entrySet()) {
            var day = entry.getKey();
            var ok = entry.getValue() >= minPerDay;

            if (last == null) {
                cur = ok ? 1 : 0;
            } else {
                if (!day.equals(last.plusDays(1))) {
                    cur = ok ? 1 : 0;
                } else {
                    cur = ok ? (cur + 1) : 0;
                }
            }
            max = Math.max(max, cur);
            last = day;
        }

        int current;
        var lastEntry = counts.get(today);
        if (lastEntry != null && lastEntry >= minPerDay) {
            current = cur;
        } else {
            current = 0;
        }

        return new Result(current, max, minPerDay);
    }
}
