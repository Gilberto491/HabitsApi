package com.app.habits.domain.port;

import com.app.habits.domain.model.Habit;

import java.util.List;
import java.util.Optional;

public interface HabitRepositoryPort {
    Habit save(Habit habit);
    Optional<Habit> findById(String id);
    void deleteById(String id);
    List<Habit> findActiveByUser(String userId);
}