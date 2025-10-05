package com.app.habits.infrastructure.rest;

import com.app.habits.infrastructure.config.JwtService;
import com.app.habits.infrastructure.persistence.entity.UserEntity;
import com.app.habits.infrastructure.persistence.spring.UserRepository;
import com.app.habits.infrastructure.rest.dto.auth.LoginRequest;
import com.app.habits.infrastructure.rest.dto.auth.RegisterRequest;
import com.app.habits.infrastructure.rest.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        users.findByUsername(req.username()).ifPresent(u -> {
            throw new IllegalArgumentException("username já utilizado");
        });

        var user = new UserEntity(
                UUID.randomUUID().toString(),
                req.username(),
                req.email(),
                encoder.encode(req.password())
        );
        users.save(user);
        return Map.of("id", user.getId(), "username", user.getUsername(), "email", user.getEmail());
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        var user = users.findByUsername(req.username()).orElseThrow();

        String token = jwt.generate(user.getId(), user.getUsername());
        return new TokenResponse(token, "Bearer");
    }
}
