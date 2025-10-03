package com.app.habits.application.usecase;

import com.app.habits.domain.model.TodayHabitStatus;
import com.app.habits.domain.port.CheckInRepositoryPort;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class ListTodayWithStatusUseCase {

    private final CheckInRepositoryPort checkIns;
    private final Clock clock;

    public ListTodayWithStatusUseCase(CheckInRepositoryPort checkIns, Clock clock) {
        this.checkIns = checkIns;
        this.clock = clock;
    }

    public List<TodayHabitStatus> execute(String userId) {
        return checkIns.listTodayStatusByUser(userId, LocalDate.now(clock));
    }
}
