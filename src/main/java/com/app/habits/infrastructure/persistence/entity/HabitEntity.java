package com.app.habits.infrastructure.persistence.entity;

import com.app.habits.domain.model.Difficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "habits")
public class HabitEntity {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private String id;

    @Column(name = "user_id", columnDefinition = "uuid", nullable = false)
    private String userId;

    @Column(name = "category_id", columnDefinition = "uuid", nullable = false)
    private String categoryId;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "target_per_week", nullable = false)
    private Integer targetPerWeek;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty;

    @Column(name = "points_per_checkin", nullable = false)
    private Integer pointsPerCheckIn;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "base_points", nullable = false)
    private int basePoints = 10;

    @Column(name = "created_at", nullable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @PrePersist
    void prePersist() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }
}
