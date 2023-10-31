package com.cstore.filter;

import com.cstore.dao.user.token.TokenDao;
import com.cstore.model.user.Token;
import com.cstore.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenDao tokenDao;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String content;
        final String subject;

        if (
            StringUtils.isEmpty(authHeader) ||
            !StringUtils.startsWith(authHeader, "Bearer ")
        ) {
            filterChain.doFilter(request, response);
        }
        else {
            content = authHeader.substring(7);
            log.debug("JWT: {}", content);

            subject = jwtService.extractSubject(content);

            if (
                StringUtils.isNotEmpty(subject) &&
                SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
                Boolean isTokenValid = tokenDao
                    .findByContent(content)
                    .map(token -> !token.getExpired() && !token.getRevoked())
                    .orElse(false);

                if (jwtService.isJwtValid(content, userDetails) && isTokenValid) {
                    log.debug("User: {}", userDetails);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}
