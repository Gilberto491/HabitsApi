package com.app.habits.application.dto;

import com.app.habits.domain.model.Difficulty;

public record HabitTemplateDto(String id, String categoryId, String name, String description,
                               Integer defaultWeeklyTarget, Difficulty difficulty, Integer basePoints) {
}
