package com.app.habits.infrastructure.rest;

import com.app.habits.application.usecase.GetOverallStreakUseCase;
import com.app.habits.domain.model.AuthenticatedUser;
import com.app.habits.domain.model.GamificationProgress;
import com.app.habits.domain.port.GamificationProgressPort;
import com.app.habits.infrastructure.rest.dto.OverallStreakDto;
import com.app.habits.infrastructure.rest.dto.ProgressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final GamificationProgressPort progressPort;
    private final GetOverallStreakUseCase getOverallStreakUseCase;

    @GetMapping("/progress")
    public ResponseEntity<ProgressResponseDto> getProgress(
            @AuthenticationPrincipal AuthenticatedUser user) {

        var progress = progressPort.findByUserId(user.id())
                .orElseGet(() -> new GamificationProgress(user.id()));

        return ResponseEntity.ok(ProgressResponseDto.from(progress));
    }

    @GetMapping("/streak")
    public ResponseEntity<OverallStreakDto> getOverallStreak(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestParam(name = "min", defaultValue = "2") int minPerDay) {
        var r = getOverallStreakUseCase.execute(user.id(), minPerDay);
        return ResponseEntity.ok(new OverallStreakDto(r.currentStreak(), r.maxStreak(), r.minPerDay()));
    }
}
