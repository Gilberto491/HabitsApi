package com.app.habits.infrastructure.rest.dto.auth;

public record TokenResponse(String access_token, String token_type) {}