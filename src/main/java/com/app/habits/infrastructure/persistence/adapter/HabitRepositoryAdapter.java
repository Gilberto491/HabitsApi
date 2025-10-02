package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.Habit;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.persistence.entity.HabitEntity;
import com.app.habits.infrastructure.persistence.mapper.HabitEntityMapper;
import com.app.habits.infrastructure.persistence.spring.HabitJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HabitRepositoryAdapter implements HabitRepositoryPort {

    private final HabitJpaRepository jpa;

    @Override
    public Habit save(Habit habit) {
        HabitEntity saved = jpa.save(HabitEntityMapper.toEntity(habit));
        return HabitEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Habit> findById(String id) {
        return jpa.findById(id).map(HabitEntityMapper::toDomain);
    }

    @Override
    public void deleteById(String id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Habit> findActiveByUser(String userId) {
        return jpa.findByUserIdAndActiveTrueOrderByCreatedAtDesc(userId)
                .stream().map(HabitEntityMapper::toDomain).toList();
    }
}
