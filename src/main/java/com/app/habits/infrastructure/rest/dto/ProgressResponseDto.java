package com.app.habits.infrastructure.rest.dto;

import com.app.habits.domain.model.GamificationProgress;

public record ProgressResponseDto(
        long totalXp,
        int level,
        long xpIntoLevel,
        long xpToNextLevel,
        double progressPercent
) {
    public static ProgressResponseDto from(GamificationProgress p) {
        return new ProgressResponseDto(
                p.getTotalXp(),
                p.getLevel(),
                p.getXpIntoLevel(),
                GamificationProgress.xpToNextLevel(p.getLevel()),
                p.progressPercent()
        );
    }
}
