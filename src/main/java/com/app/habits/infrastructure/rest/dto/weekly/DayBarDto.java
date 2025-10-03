package com.app.habits.infrastructure.rest.dto.weekly;

import java.time.LocalDate;

public record DayBarDto(LocalDate date, long doneCount) {}
