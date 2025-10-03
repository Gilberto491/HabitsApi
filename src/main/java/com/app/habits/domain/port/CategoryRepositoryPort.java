package com.app.habits.domain.port;

import com.app.habits.domain.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryRepositoryPort {
    List<Category> findAll();

    Category findById(String id);

    List<Category> findByIds(Set<String> ids);
}
