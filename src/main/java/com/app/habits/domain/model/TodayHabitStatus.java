package com.app.habits.domain.model;

import java.time.OffsetDateTime;


public record TodayHabitStatus(String habitId, String name, boolean done, OffsetDateTime checkedAt) {
}
