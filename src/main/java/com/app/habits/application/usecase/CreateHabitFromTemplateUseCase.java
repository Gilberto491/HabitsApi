package com.app.habits.application.usecase;

import com.app.habits.domain.exception.NotFoundException;
import com.app.habits.domain.model.Habit;
import com.app.habits.domain.model.HabitTemplate;
import com.app.habits.domain.port.HabitRepositoryPort;
import com.app.habits.domain.port.HabitTemplateRepositoryPort;
import com.app.habits.infrastructure.config.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHabitFromTemplateUseCase {
    private final HabitTemplateRepositoryPort templateRepo;
    private final HabitRepositoryPort habitRepo;
    private final MessageProvider messages;

    public Habit execute(String templateId, Habit overrides, String userId) {
        HabitTemplate tpl = templateRepo.findById(templateId)
                .orElseThrow(() -> new NotFoundException(messages.get("template.notfound")));
        Habit base = Habit.builder()
                .categoryId(firstNonNull(overrides.getCategoryId(), tpl.categoryId()))
                .name(firstNonNull(overrides.getName(), tpl.name()))
                .description(firstNonNull(overrides.getDescription(), tpl.description()))
                .targetPerWeek(firstNonNull(overrides.getTargetPerWeek(), tpl.defaultWeeklyTarget()))
                .difficulty(firstNonNull(overrides.getDifficulty(), tpl.difficulty()))
                .pointsPerCheckIn(firstNonNull(overrides.getPointsPerCheckIn(), tpl.basePoints()))
                .active(firstNonNull(overrides.getActive(), true))
                .build();
        base.setUserId(userId);
        return habitRepo.save(base);
    }

    private static <T> T firstNonNull(T a, T b) { return a != null ? a : b; }
}
