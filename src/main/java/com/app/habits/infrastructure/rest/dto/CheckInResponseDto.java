package com.app.habits.infrastructure.rest.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record CheckInResponseDto(String habitId, LocalDate checkedOn, OffsetDateTime checkedAt) {}
