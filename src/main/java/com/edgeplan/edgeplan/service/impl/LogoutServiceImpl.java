package com.edgeplan.edgeplan.service.impl;

import com.edgeplan.edgeplan.model.BlacklistedToken;
import com.edgeplan.edgeplan.repository.BlacklistedTokenRepository;
import com.edgeplan.edgeplan.service.LogoutService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final BlacklistedTokenRepository repo;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void logout(String bearerToken) {
        String token = extractToken(bearerToken);
        if (token == null || repo.existsByToken(token)) return;

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date exp = claims.getExpiration();
        Instant expiresAt = exp != null ? exp.toInstant() : Instant.now().plusSeconds(3600);

        BlacklistedToken bl = new BlacklistedToken();
        bl.setToken(token);
        bl.setExpiresAt(expiresAt);
        repo.save(bl);
    }

    private String extractToken(String bearer) {
        if (bearer == null || !bearer.startsWith("Bearer ")) return null;
        return bearer.substring(7);
    }
}