package com.app.habits.domain.port;

import com.app.habits.domain.model.GamificationProgress;

import java.util.Optional;

public interface GamificationProgressPort {
    Optional<GamificationProgress> findByUserId(String userId);
    GamificationProgress save(GamificationProgress progress);
}
