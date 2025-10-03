package com.app.habits.infrastructure.persistence.spring;

import com.app.habits.infrastructure.persistence.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressJpaRepository extends JpaRepository<UserProgressEntity, String> {}
