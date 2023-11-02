package com.cstore.configuration;

import com.cstore.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.cstore.model.user.Role.*;

@Configuration
@EnableMethodSecurity @EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter authFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource(
    ) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:3001"
        ));
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"
        ));
        config.setAllowedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/api/v1/**",
                    /*"/api/v1/auth/**",
                    "/api/v1/categories/browse/**",
                    "/api/v1/products/browse",
                    "/api/v1/products/browse/**",
                    "/api/v1/products/select/**",
                    "/api/v1/all/reports/**", // Haven't checked yet.*/

                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
                ).permitAll()
                /*.requestMatchers(
                    "/api/v1/cust/orders/buy-now",
                    "/api/v1/cust/orders/buy-now/**"
                ).hasAnyRole(GUEST_CUST.name(), REG_CUST.name())
                .requestMatchers(
                    "/api/v1/reg-user/carts/**",
                    "/api/v1/reg-cust/orders/checkout/**",
                    "/api/v1/reg-cust/orders/confirm/**",
                    "/api/v1/reg-cust/reports/customer-order/**"
                ).hasRole(REG_CUST.name())
                .requestMatchers(
                    "/api/v1/products/edit",
                    "/api/v1/variant/stock",
                    "/api/v1/admin/reports/quarterly-sales"
                ).hasRole(ADMIN.name())*/
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authProvider)
            .addFilterBefore(
                authFilter,
                UsernamePasswordAuthenticationFilter.class
            )
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(
                (request, response, authentication) ->
                    SecurityContextHolder.clearContext()
            );

        return http.build();
    }
}
