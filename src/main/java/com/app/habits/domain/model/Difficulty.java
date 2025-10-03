package com.app.habits.domain.model;

public enum Difficulty {
    EASY(1.0), MEDIUM(1.2), HARD(1.5);

    private final double multiplier;
    Difficulty(double multiplier) { this.multiplier = multiplier; }
    public double multiplier() { return multiplier; }
}
