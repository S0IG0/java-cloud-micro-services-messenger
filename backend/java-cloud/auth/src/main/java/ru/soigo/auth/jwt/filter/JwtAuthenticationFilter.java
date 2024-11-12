package ru.soigo.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.soigo.auth.jwt.converter.JwtAuthenticationConverter;

import java.io.IOException;

/**
 * Filter that processes JWT authentication for each incoming HTTP request.
 * <p>
 * This filter extracts the JWT from the request using {@link JwtAuthenticationConverter},
 * validates it, and sets the resulting {@link Authentication} in the {@link SecurityContextHolder}.
 * It extends {@link OncePerRequestFilter} to ensure it is executed only once per request.
 * </p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@link JwtAuthenticationConverter} - Converter for extracting and validating JWT from the request.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code jwtAuthenticationConverter} - Final reference to the JWT authentication converter.</li>
 * </ul>
 *
 * @see JwtAuthenticationConverter
 * @see OncePerRequestFilter
 * @see SecurityContextHolder
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final JwtAuthenticationConverter jwtAuthenticationConverter;

    /**
     * Filters the incoming request, extracting and validating the JWT, and setting the resulting
     * {@link Authentication} in the {@link SecurityContextHolder}.
     *
     * @param request     the incoming HTTP request.
     * @param response    the HTTP response.
     * @param filterChain the filter chain.
     * @throws ServletException if an error occurs during filtering.
     * @throws IOException      if an I/O error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = jwtAuthenticationConverter.convert(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
