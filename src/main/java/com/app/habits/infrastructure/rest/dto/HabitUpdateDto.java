package com.app.habits.infrastructure.rest.dto;

import com.app.habits.domain.model.Difficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HabitUpdateDto(
        @NotNull String categoryId,
        @NotBlank @Size(max = 80) String name,
        @Size(max = 500) String description,
        @NotNull @Min(1) @Max(7) Integer targetPerWeek,
        @NotNull Difficulty difficulty,
        @NotNull @Min(1) @Max(1000) Integer pointsPerCheckIn
) {}
