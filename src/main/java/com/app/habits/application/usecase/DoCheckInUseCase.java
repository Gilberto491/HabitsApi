package com.app.habits.application.usecase;

import com.app.habits.domain.exception.ApiException;
import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.rest.error.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@AllArgsConstructor
public class DoCheckInUseCase {

    private final CheckInRepositoryPort checkIns;
    private final HabitRepositoryPort habits;
    private final Clock clock;

    public CheckIn execute(String userId, String habitId) {
        var habit = habits.findById(habitId)
                .orElseThrow(() -> new ApiException(HttpStatus.FOUND, ErrorCode.NOT_FOUND, null));

        if (!habit.getUserId().equals(userId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, null);
        }

        var today = LocalDate.now(clock);
        if (checkIns.existsByHabitAndDay(habitId, today)) {
            throw new ApiException(HttpStatus.ALREADY_REPORTED, ErrorCode.ALREADY_REPORTED, null);
        }

        var now = OffsetDateTime.now(clock);
        return checkIns.save(new CheckIn(null, habitId, today, now));
    }
}
