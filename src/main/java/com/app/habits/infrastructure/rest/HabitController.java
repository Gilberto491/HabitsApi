package com.app.habits.infrastructure.rest;

import com.app.habits.application.usecase.CreateHabitFromTemplateUseCase;
import com.app.habits.application.usecase.CreateHabitUseCase;
import com.app.habits.application.usecase.DeleteHabitUseCase;
import com.app.habits.application.usecase.DoCheckInUseCase;
import com.app.habits.application.usecase.GetCurrentWeekOverviewUseCase;
import com.app.habits.application.usecase.ListTodayWithStatusUseCase;
import com.app.habits.application.usecase.ToggleHabitActiveUseCase;
import com.app.habits.application.usecase.UndoTodayCheckInUseCase;
import com.app.habits.application.usecase.UpdateHabitUseCase;
import com.app.habits.domain.model.AuthenticatedUser;
import com.app.habits.infrastructure.persistence.mapper.HabitMapper;
import com.app.habits.infrastructure.rest.dto.CheckInResponseDto;
import com.app.habits.infrastructure.rest.dto.HabitCreateDto;
import com.app.habits.infrastructure.rest.dto.HabitResponseDto;
import com.app.habits.infrastructure.rest.dto.HabitToggleDto;
import com.app.habits.infrastructure.rest.dto.HabitUpdateDto;
import com.app.habits.infrastructure.rest.dto.TodayHabitDto;
import com.app.habits.infrastructure.rest.dto.weekly.WeeklyOverviewDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
public class HabitController {

    private final CreateHabitUseCase createUC;
    private final CreateHabitFromTemplateUseCase createFromTplUC;
    private final UpdateHabitUseCase updateUC;
    private final ToggleHabitActiveUseCase toggleUC;
    private final DeleteHabitUseCase deleteUC;
    private final DoCheckInUseCase doCheckInUseCase;
    private final ListTodayWithStatusUseCase listTodayWithStatusUseCase;
    private final UndoTodayCheckInUseCase undoTodayCheckInUseCase;
    private final GetCurrentWeekOverviewUseCase getCurrentWeekOverviewUseCase;

    private String currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser user) {
            return user.id();
        }
        throw new IllegalStateException("Usuário não autenticado");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitResponseDto create(@RequestBody @Valid HabitCreateDto req) {
        if (req.templateId() != null && !req.templateId().isBlank()) {
            var res = createFromTplUC.execute(req.templateId(), HabitMapper.toDomain(req), currentUserId());
            return HabitMapper.toResponse(res);
        }
        var res = createUC.execute(HabitMapper.toDomain(req), currentUserId());
        return HabitMapper.toResponse(res);
    }

    @PutMapping("/{id}")
    public HabitResponseDto update(@PathVariable String id, @RequestBody @Valid HabitUpdateDto req) {
        var res = updateUC.execute(id, HabitMapper.toDomain(req), currentUserId());
        return HabitMapper.toResponse(res);
    }

    @PatchMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggle(@PathVariable String id, @RequestBody @Valid HabitToggleDto req) {
        toggleUC.execute(id, req.active(), currentUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteUC.execute(id, currentUserId());
    }

    @PostMapping("/{habitId}/checkins")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckInResponseDto doCheckIn(@PathVariable String habitId,
                                        @AuthenticationPrincipal AuthenticatedUser user) {
        var c = doCheckInUseCase.execute(user.id(), habitId);
        return new CheckInResponseDto(c.getHabitId(), c.getCheckedOn(), c.getCheckedAt());
    }

    @DeleteMapping("/{habitId}/checkins/today")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void undoToday(@PathVariable String habitId,
                          @AuthenticationPrincipal AuthenticatedUser user) {
        undoTodayCheckInUseCase.execute(user.id(), habitId);
    }

    @GetMapping("/today")
    public List<TodayHabitDto> listToday(@AuthenticationPrincipal AuthenticatedUser user) {
        return listTodayWithStatusUseCase.execute(user.id()).stream()
                .map(t -> new TodayHabitDto(t.habitId(), t.name(), t.done(), t.checkedAt()))
                .toList();
    }

    @GetMapping("/overview/week")
    public WeeklyOverviewDto getWeeklyOverview(@AuthenticationPrincipal AuthenticatedUser user) {
        return getCurrentWeekOverviewUseCase.execute(user);
    }
}
