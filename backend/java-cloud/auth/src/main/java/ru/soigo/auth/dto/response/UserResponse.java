package ru.soigo.auth.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.soigo.auth.dto.shared.ObjectWithUUID;

import java.util.List;

/**
 * Data Transfer Object (DTO) for responding with user details.
 * <p>
 * This class represents the response containing user information,
 * typically used for providing user details in response to a request.
 * It extends {@link ObjectWithUUID} to include a UUID identifier.
 * </p>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@link AllArgsConstructor} - Generates a constructor with one parameter for each field in the class.</li>
 *   <li>{@link NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@link SuperBuilder} - Provides a builder pattern for object creation with inheritance support.</li>
 *   <li>{@link Getter} - Generates getters for all fields.</li>
 *   <li>{@link Setter} - Generates setters for all fields.</li>
 *   <li>{@link ToString} - Generates a toString method that includes all fields.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code username} - The username of the user.</li>
 *   <li>{@code email} - The email address of the user.</li>
 *   <li>{@code first_name} - The first name of the user.</li>
 *   <li>{@code last_name} - The last name of the user.</li>
 *   <li>{@code roles} - The roles assigned to the user.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * UserResponse userResponse = UserResponse.builder()
 *                                         .id(UUID.randomUUID())
 *                                         .username("user123")
 *                                         .email("user@example.com")
 *                                         .firstName("John")
 *                                         .lastName("Doe")
 *                                         .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
 *                                         .build();
 * }
 * </pre>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.experimental.SuperBuilder
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.ToString
 * @see ru.soigo.auth.dto.shared.ObjectWithUUID
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class UserResponse extends ObjectWithUUID {
    /**
     * The username of the user.
     */
    String username;

    /**
     * The email address of the user.
     */
    String email;

    /**
     * The first name of the user.
     */
    String first_name;

    /**
     * The last name of the user.
     */
    String last_name;

    /**
     * The roles assigned to the user.
     */
    List<String> roles;
}
