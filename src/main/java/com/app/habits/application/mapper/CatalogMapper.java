package com.app.habits.application.mapper;

import com.app.habits.application.dto.CategoryDto;
import com.app.habits.application.dto.HabitTemplateDto;
import com.app.habits.domain.model.Category;
import com.app.habits.domain.model.HabitTemplate;
import com.app.habits.infrastructure.persistence.entity.CategoryEntity;
import com.app.habits.infrastructure.persistence.entity.HabitTemplateEntity;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {
    public static CategoryDto toDto(Category c) {
        return new CategoryDto(c.id(), c.name(), c.colorHex());
    }

    public static HabitTemplateDto toDto(HabitTemplate t) {
        return new HabitTemplateDto(t.id(), t.categoryId(), t.name(),
                t.description(), t.defaultWeeklyTarget(), t.difficulty(), t.basePoints());
    }

    public Category toDomain(CategoryEntity e) {
        if (e == null) return null;
        return new Category(
                e.getId(),
                e.getColorHex(),
                e.getName()
        );
    }

    public HabitTemplate toDomain(HabitTemplateEntity e) {
        if (e == null) return null;
        return new HabitTemplate(
                e.getId(),
                e.getCategoryId(),
                e.getName(),
                e.getDescription(),
                e.getDefaultWeeklyTarget(),
                e.getDifficulty(),
                e.getBasePoints()
        );
    }
}