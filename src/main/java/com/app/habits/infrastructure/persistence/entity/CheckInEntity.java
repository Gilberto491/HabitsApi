package com.app.habits.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "check_ins", uniqueConstraints = {
        @UniqueConstraint(name = "uk_checkin_habit_day", columnNames = {"habit_id", "checked_on"})
})
public class CheckInEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(name = "habit_id", nullable = false)
    private String habitId;

    @Column(name = "checked_on", nullable = false)
    private LocalDate checkedOn;

    @Column(name = "checked_at", nullable = false)
    private OffsetDateTime checkedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        if (checkedAt == null) checkedAt = OffsetDateTime.now();
    }
}
