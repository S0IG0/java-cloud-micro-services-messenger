package ru.soigo.auth.model;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
/**
 * Represents the roles that can be assigned to a user within the system.
 * <p>
 * This enum implements {@link GrantedAuthority} to integrate with Spring Security for role-based authorization.
 * The roles defined here are:
 * </p>
 * <ul>
 *   <li>{@code ROLE_USER} - Standard user role with limited permissions.</li>
 *   <li>{@code ROLE_ADMIN} - Administrative user role with elevated permissions.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * User user = User.builder()
 *                 .username("john_doe")
 *                 .email("john.doe@example.com")
 *                 .firstName("John")
 *                 .lastName("Doe")
 *                 .password("securepassword")
 *                 .roles(Set.of(Role.USER))
 *                 .build();
 * }</pre>
 *
 * @see GrantedAuthority
 * @see User
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;
    /**
     * Returns the authority granted by this role.
     *
     * @return the name of this role
     */
    @Contract(pure = true)
    @Override
    public @NotNull String getAuthority() {
        return name();
    }
}