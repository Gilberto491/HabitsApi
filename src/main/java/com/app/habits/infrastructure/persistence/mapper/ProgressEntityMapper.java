package com.app.habits.infrastructure.persistence.mapper;

import com.app.habits.domain.model.GamificationProgress;
import com.app.habits.infrastructure.persistence.entity.UserProgressEntity;

import java.lang.reflect.Field;

public class ProgressEntityMapper {
    private ProgressEntityMapper() {
    }

    public static GamificationProgress toDomain(UserProgressEntity e) {
        GamificationProgress p = new GamificationProgress(e.getUserId());
        try {
            Field fTotal = GamificationProgress.class.getDeclaredField("totalXp");
            Field fLevel = GamificationProgress.class.getDeclaredField("level");
            Field fInto = GamificationProgress.class.getDeclaredField("xpIntoLevel");
            fTotal.setAccessible(true);
            fLevel.setAccessible(true);
            fInto.setAccessible(true);
            fTotal.setLong(p, e.getTotalXp());
            fLevel.setInt(p, e.getLevel());
            fInto.setLong(p, e.getXpIntoLevel());
        } catch (Exception ignore) {
        }
        return p;
    }

    public static UserProgressEntity toEntity(GamificationProgress p) {
        UserProgressEntity e = new UserProgressEntity();
        e.setUserId(p.getUserId());
        e.setTotalXp(p.getTotalXp());
        e.setLevel(p.getLevel());
        e.setXpIntoLevel(p.getXpIntoLevel());
        return e;
    }
}
