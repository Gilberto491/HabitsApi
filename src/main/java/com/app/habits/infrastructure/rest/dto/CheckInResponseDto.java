package com.app.habits.infrastructure.rest.dto;

import com.app.habits.application.usecase.DoCheckInUseCase;
import com.app.habits.domain.model.GamificationProgress;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record CheckInResponseDto(
        String habitId,
        LocalDate checkedOn,
        OffsetDateTime checkedAt,
        long gainedXp,
        long totalXp,
        int level,
        long xpIntoLevel,
        long xpToNextLevel,
        double progressPercent

) {
    public static CheckInResponseDto from(DoCheckInUseCase.Result r) {
        var c = r.checkIn();
        var p = r.progress();
        return new CheckInResponseDto(
                c.getHabitId(),
                c.getCheckedOn(),
                c.getCheckedAt(),
                r.gainedXp(),
                p.getTotalXp(),
                p.getLevel(),
                p.getXpIntoLevel(),
                GamificationProgress.xpToNextLevel(p.getLevel()),
                p.progressPercent()
        );
    }
}
