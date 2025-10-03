package com.app.habits.infrastructure.persistence.adapter;

import com.app.habits.domain.model.CheckIn;
import com.app.habits.domain.model.TodayHabitStatus;
import com.app.habits.domain.port.CheckInRepositoryPort;
import com.app.habits.infrastructure.persistence.mapper.CheckInEntityMapper;
import com.app.habits.infrastructure.persistence.spring.CheckInJpaRepository;

import java.time.LocalDate;
import java.util.List;

public class CheckInRepositoryAdapter implements CheckInRepositoryPort {

    private final CheckInJpaRepository repo;

    public CheckInRepositoryAdapter(CheckInJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean existsByHabitAndDay(String habitId, LocalDate day) {
        return repo.existsByHabitIdAndCheckedOn(habitId, day);
    }

    @Override
    public CheckIn save(CheckIn checkIn) {
        var saved = repo.save(CheckInEntityMapper.toEntity(checkIn));
        return CheckInEntityMapper.toDomain(saved);
    }

    @Override
    public int deleteByHabitAndDay(String habitId, LocalDate day) {
        return repo.deleteByHabitIdAndCheckedOn(habitId, day);
    }

    @Override
    public List<TodayHabitStatus> listTodayStatusByUser(String userId, LocalDate day) {
        return repo.listTodayStatus(userId, day)
                .stream()
                .map(r -> CheckInEntityMapper.toTodayStatus(r.getHabitId(), r.getName(),
                        Boolean.TRUE.equals(r.getDone()), r.getCheckedAt()))
                .toList();
    }
}
