package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {
}