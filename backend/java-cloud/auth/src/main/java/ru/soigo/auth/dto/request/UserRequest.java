package ru.soigo.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object (DTO) for handling user registration or update requests.
 * <p>
 * This class represents a request containing user information, typically used
 * for creating a new user or updating an existing user's details.
 * </p>
 * <p>
 * The class includes validation annotations to ensure that the username,
 * password, email, first name, and last name are not null and not blank. Additionally,
 * the email field is validated to ensure it conforms to a valid email format.
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
 *   <li>{@code email} - The email address of the user. Must not be null or blank and must be in a valid email format.</li>
 *   <li>{@code first_name} - The first name of the user. Must not be null or blank.</li>
 *   <li>{@code last_name} - The last name of the user. Must not be null or blank.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * UserRequest userRequest = UserRequest.builder()
 *                                      .username("user123")
 *                                      .password("password")
 *                                      .email("user@example.com")
 *                                      .firstName("John")
 *                                      .lastName("Doe")
 *                                      .build();
 * }
 * </pre>
 *
 * @see jakarta.validation.constraints.Email
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
public class UserRequest {
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

    /**
     * The email address of the user. Must not be null or blank and must be in a valid email format.
     */
    @NotBlank
    @NotNull
    @Email
    String email;

    /**
     * The first name of the user. Must not be null or blank.
     */
    @NotBlank
    @NotNull
    String first_name;

    /**
     * The last name of the user. Must not be null or blank.
     */
    @NotBlank
    @NotNull
    String last_name;
}
