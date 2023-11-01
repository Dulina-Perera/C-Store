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
    @Value("${application.security.token.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.token.access.expiration-ms}")
    private Long ACCESS_EXPIRATION_MS;

    @Value("${application.security.token.refresh.expiration-ms}")
    private Long REFRESH_EXPIRATION_MS;





    private String buildToken(
        Map<String, Object> claims,
        UserDetails userDetails,
        Long accessExpirationMs
    ) {
        return Jwts
            .builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + accessExpirationMs))
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact();
    }

    private SecretKey getSigningKey(
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
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

    private boolean isJwtExpired(
        String jwt
    ) {
        return extractExpiration(jwt).before(new Date());
    }





    public String extractSubject(
        String jwt
    ) {
        return extractClaim(
            jwt,
            Claims::getSubject
        );
    }

    public String generateRefreshToken(
        UserDetails userDetails
    ) {
        return buildToken(
            new HashMap<>(),
            userDetails,
            REFRESH_EXPIRATION_MS
        );
    }

    public String generateAccessToken(
        Map<String, Object> claims,
        UserDetails userDetails
    ) {
        return buildToken(
            claims,
            userDetails,
            ACCESS_EXPIRATION_MS
        );
    }

    public String generateAccessTokenWithoutClaims(
        UserDetails userDetails
    ) {
        return buildToken(
            new HashMap<>(),
            userDetails,
            ACCESS_EXPIRATION_MS
        );
    }

    public boolean isTokenValid(
        String jwt,
        UserDetails userDetails
    ) {
        final String subject = extractSubject(jwt);
        return
            (subject.equals(userDetails.getUsername())) &&
            !isJwtExpired(jwt);
    }
}
