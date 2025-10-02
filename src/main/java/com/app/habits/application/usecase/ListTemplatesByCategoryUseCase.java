package com.app.habits.application.usecase;

import com.app.habits.application.dto.HabitTemplateDto;
import com.app.habits.application.mapper.CatalogMapper;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;

import java.util.List;

public class ListTemplatesByCategoryUseCase {
    private final HabitTemplateRepositoryPort repo;

    public ListTemplatesByCategoryUseCase(HabitTemplateRepositoryPort repo) {
        this.repo = repo;
    }

    public List<HabitTemplateDto> execute(String categoryId) {
        return repo.findByCategory(categoryId).stream().map(CatalogMapper::toDto).toList();
    }
}
