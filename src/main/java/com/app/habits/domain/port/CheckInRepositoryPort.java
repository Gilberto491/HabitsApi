package com.app.habits.domain.port;

import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.model.TodayHabitStatus;

import java.time.LocalDate;
import java.util.List;

public interface CheckInRepositoryPort {

    boolean existsByHabitAndDay(String habitId, LocalDate day);
    CheckIn save(CheckIn checkIn);
    int deleteByHabitAndDay(String habitId, LocalDate day);
    List<TodayHabitStatus> listTodayStatusByUser(String userId, LocalDate day);
}
