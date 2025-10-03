package com.app.habits.infrastructure.persistence.mapper;

import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.model.TodayHabitStatus;
import com.app.habits.infrastructure.persistence.entity.CheckInEntity;

import java.time.OffsetDateTime;

public class CheckInEntityMapper {

    public static CheckInEntity toEntity(CheckIn c) {
        return new CheckInEntity(c.getId(), c.getUserId(), c.getHabitId(), c.getCheckedOn(), c.getCheckedAt());
    }

    public static CheckIn toDomain(CheckInEntity e) {
        return new CheckIn(e.getId(), e.getUserId(), e.getHabitId(), e.getCheckedOn(), e.getCheckedAt());
    }

    public static TodayHabitStatus toTodayStatus(String habitId, String name, boolean done, OffsetDateTime checkedAt) {
        return new TodayHabitStatus(habitId, name, done, checkedAt);
    }
}
