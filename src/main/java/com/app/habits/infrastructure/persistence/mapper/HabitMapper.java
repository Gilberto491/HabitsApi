package com.app.habits.infrastructure.persistence.mapper;

import com.app.habits.domain.model.Habit;
import com.app.habits.infrastructure.rest.dto.HabitCreateDto;
import com.app.habits.infrastructure.rest.dto.HabitResponseDto;
import com.app.habits.infrastructure.rest.dto.HabitUpdateDto;

public final class HabitMapper {

    private HabitMapper(){}

    public static Habit toDomain(HabitCreateDto d) {
        return Habit.builder()
                .categoryId(d.categoryId())
                .name(d.name())
                .description(d.description())
                .targetPerWeek(d.targetPerWeek())
                .difficulty(d.difficulty())
                .pointsPerCheckIn(d.pointsPerCheckIn())
                .active(d.active())
                .build();
    }

    public static Habit toDomain(HabitUpdateDto d) {
        return Habit.builder()
                .categoryId(d.categoryId())
                .name(d.name())
                .description(d.description())
                .targetPerWeek(d.targetPerWeek())
                .difficulty(d.difficulty())
                .pointsPerCheckIn(d.pointsPerCheckIn())
                .build();
    }

    public static HabitResponseDto toResponse(Habit h) {
        return new HabitResponseDto(
                h.getId(), h.getUserId(), h.getCategoryId(), h.getName(), h.getDescription(),
                h.getTargetPerWeek(), h.getDifficulty(), h.getPointsPerCheckIn(),
                h.getActive(), h.getCreatedAt()
        );
    }
}