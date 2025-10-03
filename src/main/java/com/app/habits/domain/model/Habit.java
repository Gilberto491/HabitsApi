package com.app.habits.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Habit {

    private String id;
    private String userId;
    private String categoryId;
    private String name;
    private String description;
    private Integer targetPerWeek;
    private Difficulty difficulty;
    private Integer pointsPerCheckIn;
    private Boolean active;
    private String createdAt;
    private Category category;

}
