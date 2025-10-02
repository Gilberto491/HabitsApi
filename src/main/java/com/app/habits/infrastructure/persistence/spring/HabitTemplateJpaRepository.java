package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.HabitTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitTemplateJpaRepository extends JpaRepository<HabitTemplateEntity, String> {
    List<HabitTemplateEntity> findByCategoryId(String categoryId);
}
