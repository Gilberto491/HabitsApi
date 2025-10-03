package com.app.habits.infrastructure.rest.dto;

public record HabitStreakDto(String habitId, int currentStreak, int maxStreak) {}
