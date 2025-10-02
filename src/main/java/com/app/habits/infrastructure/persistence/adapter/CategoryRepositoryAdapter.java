package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.Category;
import com.app.habits.domain.port.CategoryRepositoryPort;
import com.app.habits.infrastructure.persistence.entity.CategoryEntity;
import com.app.habits.infrastructure.persistence.spring.CategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {
    private final CategoryJpaRepository jpa;

    public CategoryRepositoryAdapter(CategoryJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Category> findAll() {
        return jpa.findAll().stream()
                .map(e -> new Category(e.getId(), e.getName(), e.getColorHex()))
                .toList();
    }

    @Override
    public Category findById(String id) {
        CategoryEntity e = jpa.findById(id).orElseThrow();
        return new Category(e.getId(), e.getName(), e.getColorHex());
    }
}
