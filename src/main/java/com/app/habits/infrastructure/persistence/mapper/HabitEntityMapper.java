package com.app.habits.infrastructure.persistence.mapper;

import com.app.habits.domain.model.Habit;
import com.app.habits.infrastructure.persistence.entity.HabitEntity;

public class HabitEntityMapper {

    private HabitEntityMapper() {
    }

    public static Habit toDomain(HabitEntity e) {
        if (e == null) return null;
        return Habit.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .categoryId(e.getCategoryId())
                .name(e.getName())
                .description(e.getDescription())
                .targetPerWeek(e.getTargetPerWeek())
                .difficulty(e.getDifficulty())
                .pointsPerCheckIn(e.getPointsPerCheckIn())
                .active(e.getActive())
                .createdAt(e.getCreatedAt() != null ? e.getCreatedAt().toString() : null)
                .build();
    }

    public static HabitEntity toEntity(Habit h) {
        if (h == null) return null;
        HabitEntity e = new HabitEntity();
        e.setId(h.getId());
        e.setUserId(h.getUserId());
        e.setCategoryId(h.getCategoryId());
        e.setName(h.getName());
        e.setDescription(h.getDescription());
        e.setTargetPerWeek(h.getTargetPerWeek());
        e.setDifficulty(h.getDifficulty());
        e.setPointsPerCheckIn(h.getPointsPerCheckIn());
        e.setActive(h.getActive() != null ? h.getActive() : true);
        return e;
    }
}
