package com.app.habits.infrastructure.rest.dto.weekly;

import java.time.LocalDate;
import java.util.List;

public record WeeklyOverviewDto(
        LocalDate weekStart,
        LocalDate weekEnd,
        List<DayBarDto> days,
        List<HabitProgressDto> habits,
        List<CategoryRowDto> categories
) {
}
