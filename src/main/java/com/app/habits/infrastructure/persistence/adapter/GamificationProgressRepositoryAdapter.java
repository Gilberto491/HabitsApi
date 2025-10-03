package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.GamificationProgress;
import com.app.habits.domain.port.GamificationProgressPort;
import com.app.habits.infrastructure.persistence.mapper.ProgressEntityMapper;
import com.app.habits.infrastructure.persistence.spring.ProgressJpaRepository;

import java.util.Optional;

public class GamificationProgressRepositoryAdapter implements GamificationProgressPort {
    private final ProgressJpaRepository jpa;

    public GamificationProgressRepositoryAdapter(ProgressJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<GamificationProgress> findByUserId(String userId) {
        return jpa.findById(userId).map(ProgressEntityMapper::toDomain);
    }

    @Override
    public GamificationProgress save(GamificationProgress progress) {
        var saved = jpa.save(ProgressEntityMapper.toEntity(progress));
        return ProgressEntityMapper.toDomain(saved);
    }
}