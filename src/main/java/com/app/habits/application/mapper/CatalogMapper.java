package com.app.habits.application.mapper;

import com.app.habits.application.dto.CategoryDto;
import com.app.habits.application.dto.HabitTemplateDto;
import com.app.habits.domain.model.Category;
import com.app.habits.domain.model.HabitTemplate;

public class CatalogMapper {
    public static CategoryDto toDto(Category c) {
        return new CategoryDto(c.id(), c.name(), c.colorHex());
    }
    public static HabitTemplateDto toDto(HabitTemplate t) {
        return new HabitTemplateDto(t.id(), t.categoryId(), t.name(),
                t.description(), t.defaultWeeklyTarget(), t.difficulty(), t.basePoints());
    }
}