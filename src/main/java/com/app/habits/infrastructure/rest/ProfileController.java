package com.app.habits.infrastructure.rest;

import com.app.habits.domain.model.AuthenticatedUser;
import com.app.habits.domain.model.GamificationProgress;
import com.app.habits.domain.port.GamificationProgressPort;
import com.app.habits.infrastructure.rest.dto.ProgressResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final GamificationProgressPort progressPort;

    public ProfileController(GamificationProgressPort progressPort) {
        this.progressPort = progressPort;
    }

    @GetMapping("/progress")
    public ResponseEntity<ProgressResponseDto> getProgress(
            @AuthenticationPrincipal AuthenticatedUser user) {

        var progress = progressPort.findByUserId(user.id())
                .orElseGet(() -> new GamificationProgress(user.id()));

        return ResponseEntity.ok(ProgressResponseDto.from(progress));
    }
}
