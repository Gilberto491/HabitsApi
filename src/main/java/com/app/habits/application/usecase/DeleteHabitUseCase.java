package com.app.habits.application.usecase;

import com.app.habits.domain.exception.NotFoundException;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.infrastructure.config.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHabitUseCase {

    private final HabitRepositoryPort repository;
    private final MessageProvider messages;

    public void execute(String habitId, String userId) {
        var h = repository.findById(habitId)
                .filter(x -> x.getUserId().equals(userId))
                .orElseThrow(() -> new NotFoundException(messages.get("habit.notfound")));
        repository.deleteById(h.getId());
    }
}
