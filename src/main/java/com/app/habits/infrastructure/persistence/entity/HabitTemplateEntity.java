package com.app.habits.infrastructure.persistence.entity;

import com.app.habits.domain.model.Difficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "habit_templates")
public class HabitTemplateEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private String id;

    @Column(name = "category_id", columnDefinition = "uuid", nullable = false)
    private String categoryId;

    @Column(nullable = false, length = 80)
    private String name;

    private String description;

    @Column(name = "default_weekly_target", nullable = false)
    private Integer defaultWeeklyTarget;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty;

    @Column(name = "base_points", nullable = false)
    private Integer basePoints;
}
