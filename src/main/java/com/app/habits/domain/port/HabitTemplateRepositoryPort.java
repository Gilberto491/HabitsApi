package com.app.habits.domain.port;

import com.app.habits.domain.model.HabitTemplate;

import java.util.List;

public interface HabitTemplateRepositoryPort {
    List<HabitTemplate> findByCategory(String categoryId);
}
