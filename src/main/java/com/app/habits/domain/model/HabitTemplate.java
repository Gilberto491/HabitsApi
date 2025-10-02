package com.app.habits.domain.model;

public record HabitTemplate(String id, String categoryId, String name, String description, Integer defaultWeeklyTarget,
                            Difficulty difficulty, Integer basePoints) {
}
