package com.app.habits.infrastructure.rest.dto.weekly;

public record CategoryRowDto(
        String categoryId,
        String categoryName,
        long doneThisWeek
) {}
