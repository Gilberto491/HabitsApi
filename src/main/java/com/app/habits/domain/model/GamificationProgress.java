package com.app.habits.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamificationProgress {

    private final String userId;
    private long totalXp;
    private int level;
    private long xpIntoLevel;

    public GamificationProgress(String userId) {
        this.userId = userId;
        this.level = 1;
        this.totalXp = 0;
        this.xpIntoLevel = 0;
    }

    public static long xpToNextLevel(int level) {
        return 100L + (level * 20L);
    }

    public long applyGain(long gained) {
        long remaining = gained;
        while (remaining > 0) {
            long toNext = xpToNextLevel(level) - xpIntoLevel;
            if (remaining >= toNext) {
                remaining -= toNext;
                level++;
                xpIntoLevel = 0;
            } else {
                xpIntoLevel += remaining;
                remaining = 0;
            }
        }
        totalXp += gained;
        return gained;
    }

    public long currentLevelCap() {
        return xpToNextLevel(level);
    }

    public double progressPercent() {
        long cap = currentLevelCap();
        return cap == 0 ? 0 : (double) xpIntoLevel / (double) cap;
    }
}
