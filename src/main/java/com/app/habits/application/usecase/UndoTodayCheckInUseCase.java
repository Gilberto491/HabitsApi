package com.app.habits.application.usecase;

import com.app.habits.domain.exception.ApiException;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.rest.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

public class UndoTodayCheckInUseCase {

    private final CheckInRepositoryPort checkIns;
    private final HabitRepositoryPort habits;
    private final Clock clock;

    public UndoTodayCheckInUseCase(CheckInRepositoryPort checkIns, HabitRepositoryPort habits, Clock clock) {
        this.checkIns = checkIns;
        this.habits = habits;
        this.clock = clock;
    }

    @Transactional
    public void execute(String userId, String habitId) {
        var habit = habits.findById(habitId)
                .orElseThrow(() -> new ApiException(HttpStatus.FOUND, ErrorCode.NOT_FOUND, null));

        if (!habit.getUserId().equals(userId)) throw new ApiException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, null);

        var today = LocalDate.now(clock);
        var deleted = checkIns.deleteByHabitAndDay(habitId, today);
        if (deleted == 0) throw new ApiException(HttpStatus.FOUND, ErrorCode.NOT_FOUND, null);
    }
}
