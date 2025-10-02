package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.HabitTemplate;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;
import com.app.habits.infrastructure.persistence.spring.HabitTemplateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitTemplateRepositoryAdapter implements HabitTemplateRepositoryPort {
    private final HabitTemplateJpaRepository jpa;

    public HabitTemplateRepositoryAdapter(HabitTemplateJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<HabitTemplate> findByCategory(String categoryId) {
        return jpa.findByCategoryId(categoryId).stream()
                .map(e -> new HabitTemplate(
                        e.getId(), e.getCategoryId(), e.getName(), e.getDescription(),
                        e.getDefaultWeeklyTarget(), e.getDifficulty(), e.getBasePoints()))
                .toList();
    }
}
