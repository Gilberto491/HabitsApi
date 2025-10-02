package com.app.habits.application.usecase;

import com.app.habits.domain.exception.ApiException;
import com.app.habits.domain.model.Habit;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.config.MessageProvider;
import com.app.habits.infrastructure.rest.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHabitUseCase {
    private final HabitRepositoryPort repository;
    private final MessageProvider messages;

    public Habit execute(Habit habit, String userId) {
        validateTarget(habit.getTargetPerWeek());
        habit.setUserId(userId);
        if (habit.getActive() == null) habit.setActive(true);
        return repository.save(habit);
    }

    private void validateTarget(Integer t) {
        if (t == null || t < 1 || t > 7) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, ErrorCode.UNPROCESSABLE_ENTITY,
                    messages.get("habit.target.invalid"));
        }
    }
}
