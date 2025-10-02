package com.app.habits.application.usecase;

import com.app.habits.domain.model.Rank;

public class CalculateRankUseCase {
    public Rank execute(int totalPoints) {
        return Rank.fromPoints(totalPoints);
    }
}