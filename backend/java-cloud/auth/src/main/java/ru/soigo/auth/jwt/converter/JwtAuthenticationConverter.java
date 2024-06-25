package ru.soigo.auth.jwt.converter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ru.soigo.auth.jwt.service.JwtService;

/**
 * Implementation of {@link AuthenticationConverter} that converts a JWT from the HTTP request header
 * into an {@link Authentication} object.
 * <p>
 * This component extracts the JWT from the request, validates it using {@link JwtService}, retrieves
 * the user details from {@link UserDetailsService}, and creates an authentication token.
 * </p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@link JwtService} - Service for validating and extracting information from the JWT.</li>
 *   <li>{@link UserDetailsService} - Service for loading user-specific data.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code jwtService} - Service for JWT operations.</li>
 *   <li>{@code userDetailsService} - Service for user details retrieval.</li>
 *   <li>{@code jwtHeaderStart} - Prefix for the JWT header.</li>
 * </ul>
 *
 * @see AuthenticationConverter
 * @see JwtService
 * @see UserDetailsService
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter implements AuthenticationConverter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.header.start}")
    String jwtHeaderStart;

    /**
     * Converts the HTTP request containing the JWT into an {@link Authentication} object.
     *
     * @param request the HTTP request containing the JWT in the Authorization header.
     * @return the {@link Authentication} object if the JWT is valid, or {@code null} if the JWT is invalid or missing.
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        String token = tokenExtractor(request);

        if (!jwtService.validateAccessToken(token)) {
            return null;
        }

        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        return authenticationToken;
    }

    /**
     * Extracts the JWT from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request containing the Authorization header.
     * @return the extracted JWT if present and correctly formatted, or {@code null} if the header is missing or invalid.
     */
    private @Nullable String tokenExtractor(@NotNull HttpServletRequest request) {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerValue == null || !headerValue.startsWith(jwtHeaderStart + " ")) {
            return null;
        }

        return headerValue.replace(jwtHeaderStart + " ", "");
    }
}
