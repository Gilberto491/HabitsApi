package com.app.habits.infrastructure.config;

import com.app.habits.domain.model.AuthenticatedUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    private final String issuer;
    private final SecretKey key;
    private final Duration expiration;
    private final Clock clock;

    public JwtService(
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-minutes}") long expirationMinutes,
            Clock clock
    ) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = Duration.ofMinutes(expirationMinutes);
        this.clock = clock;
    }

    public String generate(String userId, String username) {
        Instant now = Instant.now(clock);
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiration)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public AuthenticatedUser parse(String token) {
        var jws = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setClock(() -> Date.from(Instant.now(clock)))
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        var claims = jws.getBody();
        return new AuthenticatedUser(claims.getSubject(), claims.get("username", String.class));
    }
}
