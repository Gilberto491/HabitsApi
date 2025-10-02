package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.application.mapper.CatalogMapper;
import com.app.habits.domain.model.HabitTemplate;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;
import com.app.habits.infrastructure.persistence.spring.HabitTemplateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HabitTemplateRepositoryAdapter implements HabitTemplateRepositoryPort {
    private final HabitTemplateJpaRepository jpa;
    private final CatalogMapper mapper;

    @Override
    public List<HabitTemplate> findByCategory(String categoryId) {
        return jpa.findByCategoryId(categoryId).stream()
                .map(e -> new HabitTemplate(
                        e.getId(), e.getCategoryId(), e.getName(), e.getDescription(),
                        e.getDefaultWeeklyTarget(), e.getDifficulty(), e.getBasePoints()))
                .toList();
    }

    @Override
    public Optional<HabitTemplate> findById(String id) {
        return jpa.findById(id).map(mapper::toDomain);
    }
}
