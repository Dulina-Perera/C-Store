package com.cstore.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    @Value("${token.secret.key}")
    private String jwtSecretKey;

    @Value("${token.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey(
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(
        String jwt
    ) {
        return Jwts
            .parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
    }

    private <T> T extractClaim(
        String token,
        Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(
        String jwt
    ) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public String extractSubject(
        String jwt
    ) {
        return extractClaim(
            jwt,
            Claims::getSubject
        );
    }

    public String generateToken(
        Map<String, Object> claims,
        UserDetails userDetails
    ) {
        return Jwts
            .builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact();
    }

    public String generateTokenWithoutClaims(
        UserDetails userDetails
    ) {
        return generateToken(
            new HashMap<>(),
            userDetails
        );
    }

    public boolean isJwtValid(
        String jwt,
        UserDetails userDetails
    ) {
        final String subject = extractSubject(jwt);
        return
            (subject.equals(userDetails.getUsername())) &&
            !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(
        String jwt
    ) {
        return extractExpiration(jwt).before(new Date());
    }
}
