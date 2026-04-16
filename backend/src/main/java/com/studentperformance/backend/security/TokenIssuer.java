package com.studentperformance.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenIssuer {

    private final SecretKey signingSecret;
    private final long validityWindowMs;

    public TokenIssuer(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-ms}") long validityWindowMs
    ) {
        this.signingSecret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityWindowMs = validityWindowMs;
    }

    public String issue(UserDetails principal) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + validityWindowMs);

        return Jwts.builder()
                .subject(principal.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .claim("role", principal.getAuthorities().stream().findFirst().map(Object::toString).orElse(""))
                .signWith(signingSecret)
                .compact();
    }

    public String extractUsername(String token) {
        return claims(token).getSubject();
    }

    public boolean matches(String token, UserDetails principal) {
        return principal.getUsername().equals(extractUsername(token)) && !hasExpired(token);
    }

    private boolean hasExpired(String token) {
        return claims(token).getExpiration().before(new Date());
    }

    private Claims claims(String token) {
        return Jwts.parser()
                .verifyWith(signingSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
