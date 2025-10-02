package com.app.habits.application.usecase;

import com.app.habits.domain.exception.NotFoundException;
import com.app.habits.domain.model.Habit;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.config.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHabitUseCase {
    private final HabitRepositoryPort repository;
    private final MessageProvider messages;

    public Habit execute(String habitId, Habit data, String userId) {
        Habit current = repository.findById(habitId)
                .filter(h -> h.getUserId().equals(userId))
                .orElseThrow(() -> new NotFoundException(messages.get("habit.notfound")));

        validateTarget(data.getTargetPerWeek());

        current.setCategoryId(data.getCategoryId());
        current.setName(data.getName());
        current.setDescription(data.getDescription());
        current.setTargetPerWeek(data.getTargetPerWeek());
        current.setDifficulty(data.getDifficulty());
        current.setPointsPerCheckIn(data.getPointsPerCheckIn());
        return repository.save(current);
    }

    private void validateTarget(Integer t) {
        if (t == null || t < 1 || t > 7) throw new NotFoundException(messages.get("habit.target.invalid"));
    }
}
