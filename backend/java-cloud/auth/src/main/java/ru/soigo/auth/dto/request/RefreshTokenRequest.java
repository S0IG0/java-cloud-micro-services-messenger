package ru.soigo.auth.dto.request;

import lombok.*;

/**
 * Data Transfer Object (DTO) for handling refresh token requests.
 * <p>
 * This class represents a request to refresh an authentication token.
 * It typically includes the refresh token issued during the initial
 * authentication process.
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
 * <p><b>Field:</b></p>
 * <ul>
 *   <li>{@code refresh} - The refresh token used to obtain a new authentication token.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * RefreshTokenRequest request = RefreshTokenRequest.builder()
 *                                                  .refresh("your-refresh-token")
 *                                                  .build();
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
public class RefreshTokenRequest {
    /**
     * The refresh token used to obtain a new authentication token.
     */
    String refresh;
}
