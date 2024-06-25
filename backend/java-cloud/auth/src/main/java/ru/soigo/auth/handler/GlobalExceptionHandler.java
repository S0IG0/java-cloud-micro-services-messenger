package ru.soigo.auth.handler;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.soigo.auth.exception.AlreadyUserException;
import ru.soigo.auth.handler.dto.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions across the application.
 * <p>
 * This class is annotated with {@link ControllerAdvice} to make it a global
 * exception handler for all controllers. It provides methods to handle specific
 * types of exceptions and return appropriate HTTP responses with error messages.
 * </p>
 *
 * <p><b>Exception Handlers:</b></p>
 * <ul>
 *   <li>{@link AccessDeniedException} - Handles access denied exceptions with HTTP status 403 (FORBIDDEN).</li>
 *   <li>{@link AuthenticationException}, {@link JwtException} - Handles authentication-related exceptions with HTTP status 401 (UNAUTHORIZED).</li>
 *   <li>{@link AlreadyUserException}, {@link HttpMessageNotReadableException} - Handles exceptions related to user already exists or invalid HTTP message with HTTP status 400 (BAD_REQUEST).</li>
 *   <li>{@link MethodArgumentNotValidException} - Handles validation exceptions for method arguments with HTTP status 400 (BAD_REQUEST).</li>
 * </ul>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@code handleAccessDeniedException} - Handles {@link AccessDeniedException} and returns a forbidden HTTP response.</li>
 *   <li>{@code handleAuthenticationException} - Handles {@link AuthenticationException} and {@link JwtException} and returns an unauthorized HTTP response.</li>
 *   <li>{@code handleAlreadyUserException} - Handles {@link AlreadyUserException} and {@link HttpMessageNotReadableException} and returns a bad request HTTP response.</li>
 *   <li>{@code handleMethodArgumentNotValid} - Handles {@link MethodArgumentNotValidException} and returns a bad request HTTP response with detailed validation errors.</li>
 * </ul>
 *
 * <p><b>Utility Method:</b></p>
 * <ul>
 *   <li>{@code generateMessage} - Private method to generate an {@link ErrorMessage} object containing the error message and the URL where the error occurred.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>
 * This class is automatically invoked by Spring when an exception occurs in any controller
 * method annotated with {@code @ExceptionHandler} for the specified exception types.
 * </p>
 *
 * @see ControllerAdvice
 * @see ExceptionHandler
 * @see ErrorMessage
 * @see org.springframework.security.access.AccessDeniedException
 * @see org.springframework.security.core.AuthenticationException
 * @see org.springframework.http.HttpStatus
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles AccessDeniedException and returns a forbidden HTTP response.
     *
     * @param exception The AccessDeniedException instance.
     * @param request   The HttpServletRequest where the exception occurred.
     * @return A ResponseEntity with HTTP status 403 (FORBIDDEN) and an ErrorMessage.
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(
            @NotNull AccessDeniedException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(generateMessage(request, exception.getMessage()));
    }

    /**
     * Handles AuthenticationException and JwtException and returns an unauthorized HTTP response.
     *
     * @param exception The AuthenticationException or JwtException instance.
     * @param request   The HttpServletRequest where the exception occurred.
     * @return A ResponseEntity with HTTP status 401 (UNAUTHORIZED) and an ErrorMessage.
     */
    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    public ResponseEntity<?> handleAuthenticationException(
            @NotNull AuthenticationException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(generateMessage(request, exception.getMessage()));
    }

    /**
     * Handles AlreadyUserException and HttpMessageNotReadableException and returns a bad request HTTP response.
     *
     * @param exception The RuntimeException instance.
     * @param request   The HttpServletRequest where the exception occurred.
     * @return A ResponseEntity with HTTP status 400 (BAD_REQUEST) and an ErrorMessage.
     */
    @ExceptionHandler({AlreadyUserException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleAlreadyUserException(
            @NotNull RuntimeException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(generateMessage(request, exception.getMessage()));
    }

    /**
     * Handles MethodArgumentNotValidException and returns a bad request HTTP response with detailed validation errors.
     *
     * @param exception The MethodArgumentNotValidException instance.
     * @param request   The HttpServletRequest where the exception occurred.
     * @return A ResponseEntity with HTTP status 400 (BAD_REQUEST) and an ErrorMessage containing validation errors.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException exception,
            @NotNull HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(generateMessage(request, errors));
    }

    /**
     * Private method to generate an ErrorMessage object containing the error message and the URL where the error occurred.
     *
     * @param request The HttpServletRequest where the exception occurred.
     * @param message The error message object or text.
     * @return An ErrorMessage object with the error message and URL.
     */
    private ErrorMessage generateMessage(
            @NotNull HttpServletRequest request,
            @NotNull Object message
    ) {
        return ErrorMessage
                .builder()
                .url(UrlUtils.buildFullRequestUrl(request))
                .message(message)
                .build();
    }
}
