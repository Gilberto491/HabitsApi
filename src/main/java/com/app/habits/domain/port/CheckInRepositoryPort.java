package com.app.habits.domain.port;

import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.model.TodayHabitStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CheckInRepositoryPort {

    boolean existsByHabitAndDay(String habitId, LocalDate day);
    CheckIn save(CheckIn checkIn);
    int deleteByHabitAndDay(String habitId, LocalDate day);
    List<TodayHabitStatus> listTodayStatusByUser(String userId, LocalDate day);
    List<IdCount> countByUserGroupedPerHabit(String userId, LocalDate start, LocalDate end);
    List<IdCount> countByUserGroupedPerCategory(String userId, LocalDate start, LocalDate end);
    List<DayCount> countByUserGroupedPerDay(String userId, LocalDate start, LocalDate end);
    Optional<LocalDate> findFirstCheckInDateByUser(String userId);
    Map<LocalDate, Long> countDistinctHabitsPerDay(String userId, LocalDate start, LocalDate end);
    List<LocalDate> listCheckedDaysByHabit(String habitId);

    interface DayCount { LocalDate day(); long count(); }
    interface IdCount { String id(); long count(); }
}
