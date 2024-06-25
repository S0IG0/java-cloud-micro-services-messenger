package ru.soigo.auth.jwt.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing a pair of authentication tokens.
 * <p>
 * This class is used to transfer a pair of tokens, typically consisting of an access token
 * and a refresh token, between the client and the server.
 * </p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@link #access} - A access token used to authenticate user requests and grant access to protected resources.</li>
 *   <li>{@link #refresh} - A refresh token used to obtain a new access and refresh tokens without re-authenticating the user.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * PairToken token = PairToken.builder()
 *                                     .access("access-token-value")
 *                                     .refresh("refresh-token-value")
 *                                     .build();
 *
 * System.out.println("Access Token: " + token.getAccess());
 * System.out.println("Refresh Token: " + token.getRefresh());
 * }</pre>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@link AllArgsConstructor} - Generates a constructor with one parameter for each field in the class.</li>
 *   <li>{@link NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@link Builder} - Implements the builder pattern for object creation.</li>
 *   <li>{@link Getter} - Generates getter methods for all fields.</li>
 *   <li>{@link Setter} - Generates setter methods for all fields.</li>
 *   <li>{@link ToString} - Generates a toString method that includes all fields.</li>
 * </ul>
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
public class PairToken {
    /**
     * The access token used to authenticate user requests and grant access to protected resources.
     */
    public String access;

    /**
     * The refresh token used to obtain a new access token without re-authenticating the user.
     */
    public String refresh;
}
