package com.app.habits.infrastructure.rest.dto;

import java.time.OffsetDateTime;

public record TodayHabitDto(String habitId, String name, boolean done, OffsetDateTime checkedAt) {}
