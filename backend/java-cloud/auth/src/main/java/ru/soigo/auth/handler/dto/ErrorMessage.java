package ru.soigo.auth.handler.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for representing error messages in responses.
 * <p>
 * This class encapsulates error details including a message and the URL associated
 * with the error occurrence.
 * </p>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@link AllArgsConstructor} - Generates a constructor with one parameter for each field in the class.</li>
 *   <li>{@link NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@link Builder} - Provides a builder pattern for object creation.</li>
 *   <li>{@link Getter} - Generates getters for all fields.</li>
 *   <li>{@link Setter} - Generates setters for all fields.</li>
 *   <li>{@link ToString} - Generates a toString method that includes all fields.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code message} - The error message object or text.</li>
 *   <li>{@code url} - The URL where the error occurred or a related URL.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * ErrorMessage errorMessage = ErrorMessage.builder()
 *                                         .message("An error occurred")
 *                                         .url("/api/v1/users")
 *                                         .build();
 * }
 * </pre>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.Builder
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.ToString
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorMessage {
    /**
     * The error message object or text.
     */
    public Object message;

    /**
     * The URL where the error occurred or a related URL.
     */
    public String url;
}
