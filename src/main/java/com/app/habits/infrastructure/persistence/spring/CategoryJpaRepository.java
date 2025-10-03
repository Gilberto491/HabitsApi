package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> findByIdIn(Set<String> ids);
}