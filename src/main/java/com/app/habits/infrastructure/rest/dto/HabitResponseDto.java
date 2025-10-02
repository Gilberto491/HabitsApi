package com.app.habits.infrastructure.rest.dto;

import com.app.habits.domain.model.Difficulty;

public record HabitResponseDto(
        String id,
        String userId,
        String categoryId,
        String name,
        String description,
        Integer targetPerWeek,
        Difficulty difficulty,
        Integer pointsPerCheckIn,
        Boolean active,
        String createdAt
) {}
