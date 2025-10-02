package com.app.habits.application.usecase;

import com.app.habits.application.dto.CategoryDto;
import com.app.habits.application.mapper.CatalogMapper;
import com.app.habits.domain.port.CategoryRepositoryPort;

import java.util.List;

public class ListCategoriesUseCase {
    private final CategoryRepositoryPort repo;
    public ListCategoriesUseCase(CategoryRepositoryPort repo) { this.repo = repo; }

    public List<CategoryDto> execute() {
        return repo.findAll().stream().map(CatalogMapper::toDto).toList();
    }
}
