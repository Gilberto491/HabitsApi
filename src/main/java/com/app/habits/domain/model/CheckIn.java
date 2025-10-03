package com.app.habits.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn {

    private String id;
    private String userId;
    private String habitId;
    private LocalDate checkedOn;
    private OffsetDateTime checkedAt;
}
