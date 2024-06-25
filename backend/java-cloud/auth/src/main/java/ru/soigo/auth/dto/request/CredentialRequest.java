package ru.soigo.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object (DTO) for handling credential requests.
 * <p>
 * This class represents a request containing user credentials, specifically a
 * username and a password. It is typically used for login or authentication
 * purposes.
 * </p>
 * <p>
 * The class includes validation annotations to ensure that both the username
 * and password are not null and not blank.
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
 *   <li>{@code username} - The username of the user. Must not be null or blank.</li>
 *   <li>{@code password} - The password of the user. Must not be null or blank.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * CredentialRequest request = CredentialRequest.builder()
 *                                              .username("user")
 *                                              .password("password")
 *                                              .build();
 * }
 * </pre>
 *
 * @see jakarta.validation.constraints.NotBlank
 * @see jakarta.validation.constraints.NotNull
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
public class CredentialRequest {
    /**
     * The username of the user. Must not be null or blank.
     */
    @NotBlank
    @NotNull
    String username;

    /**
     * The password of the user. Must not be null or blank.
     */
    @NotBlank
    @NotNull
    String password;
}
