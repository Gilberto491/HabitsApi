package com.app.habits.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_progress")
public class UserProgressEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "total_xp", nullable = false)
    private long totalXp;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "xp_into_level", nullable = false)
    private long xpIntoLevel;
}
