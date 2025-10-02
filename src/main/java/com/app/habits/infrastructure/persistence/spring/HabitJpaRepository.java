package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitJpaRepository extends JpaRepository<HabitEntity, String> {
    List<HabitEntity> findByUserIdAndActiveTrueOrderByCreatedAtDesc(String userId);
}
