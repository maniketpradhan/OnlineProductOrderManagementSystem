package com.maniket.oms.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Replace this with a 64+ character Base64-encoded secret in production
    private static final String SECRET =
            "TXlTdXBlclNlY3JldEtleUZvck9NU0pXVFRva2VuMTIzNDU2Nzg5MDEyMzQ1Njc4OTA=";

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String email) {

        return extractEmail(token).equals(email);

    }
}