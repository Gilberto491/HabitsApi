package com.app.habits.domain.port;

import com.app.habits.domain.model.HabitTemplate;

import java.util.List;
import java.util.Optional;

public interface HabitTemplateRepositoryPort {
    List<HabitTemplate> findByCategory(String categoryId);
    Optional<HabitTemplate> findById(String id);
}
