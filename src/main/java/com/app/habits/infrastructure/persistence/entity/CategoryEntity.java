package com.app.habits.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private String id;

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(name = "color_hex", length = 7)
    private String colorHex;
}
