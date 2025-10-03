package com.app.habits.application.usecase;

import com.app.habits.domain.model.GamificationProgress;
import com.app.habits.domain.model.Habit;

public class LevelingService {

    public long xpForCheckIn(Habit habit) {
        double mult = habit.getDifficulty().multiplier();
        int base = habit.basePoints();
        return Math.round(base * mult);
    }

    public GamificationProgress gainXp(GamificationProgress progress, long gainedXp) {
        progress.applyGain(gainedXp);
        return progress;
    }
}