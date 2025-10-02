package com.app.habits.domain.port;

import com.app.habits.domain.model.Category;

import java.util.List;

public interface CategoryRepositoryPort {
    List<Category> findAll();

    Category findById(String id);
}
