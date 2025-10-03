package com.app.habits.infrastructure.rest.dto.weekly;

public record HabitProgressDto(
        String habitId,
        String habitName,
        int targetPerWeek,
        long doneThisWeek,
        int percentAchieved
) {
}
