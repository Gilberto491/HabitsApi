package com.app.habits.infrastructure.rest;

import com.app.habits.application.usecase.CreateHabitFromTemplateUseCase;
import com.app.habits.application.usecase.CreateHabitUseCase;
import com.app.habits.application.usecase.DeleteHabitUseCase;
import com.app.habits.application.usecase.ToggleHabitActiveUseCase;
import com.app.habits.application.usecase.UpdateHabitUseCase;
import com.app.habits.infrastructure.persistence.mapper.HabitMapper;
import com.app.habits.infrastructure.rest.dto.HabitCreateDto;
import com.app.habits.infrastructure.rest.dto.HabitResponseDto;
import com.app.habits.infrastructure.rest.dto.HabitToggleDto;
import com.app.habits.infrastructure.rest.dto.HabitUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
public class HabitController {

    private final CreateHabitUseCase createUC;
    private final CreateHabitFromTemplateUseCase createFromTplUC;
    private final UpdateHabitUseCase updateUC;
    private final ToggleHabitActiveUseCase toggleUC;
    private final DeleteHabitUseCase deleteUC;

    // TODO: integrar com segurança para pegar userId real (JWT)
    private String currentUserId() {
        return "00000000-0000-0000-0000-000000000001";
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
}
