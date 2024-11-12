package ru.soigo.auth.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.soigo.auth.jwt.filter.JwtAuthenticationFilter;

/**
 * Configuration class for setting up the security configuration for the application.
 * <p>
 * This class enables web security and method security, and defines the security filter chain
 * that manages how HTTP security is configured in the application.
 * </p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@link UserDetailsService} - Service for loading user-specific data.</li>
 *   <li>{@link JwtAuthenticationFilter} - Filter for processing JWT authentication.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@link Configuration} - Indicates that this class is a configuration class.</li>
 *   <li>{@link EnableWebSecurity} - Enables web security for the application.</li>
 *   <li>{@link EnableMethodSecurity} - Enables method-level security.</li>
 * </ul>
 *
 * @see UserDetailsService
 * @see JwtAuthenticationFilter
 * @see HttpSecurity
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final UserDetailsService userDetailsService;
    final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register/**",
                                "/login/**",
                                "/refresh-pair-token/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .build();
    }
}
