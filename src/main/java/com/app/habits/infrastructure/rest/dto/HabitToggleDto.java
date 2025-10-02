package com.app.habits.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;

public record HabitToggleDto(@NotNull Boolean active) {}
