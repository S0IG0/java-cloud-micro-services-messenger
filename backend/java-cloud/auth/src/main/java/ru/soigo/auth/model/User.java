package ru.soigo.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
/**
 * Represents a user entity in the messenger application.
 * <p>
 * This class extends {@link BaseModel} to inherit common properties and implements {@link UserDetails}
 * to integrate with Spring Security for user authentication and authorization.
 * </p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code username} - The unique username of the user.</li>
 *   <li>{@code email} - The email address of the user.</li>
 *   <li>{@code first_name} - The first name of the user.</li>
 *   <li>{@code last_name} - The last name of the user.</li>
 *   <li>{@code password} - The password of the user.</li>
 *   <li>{@code roles} - The roles assigned to the user, represented as a set of {@link Role} enums.</li>
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
 * @see BaseModel
 * @see UserDetails
 * @see GrantedAuthority
 */
@Entity(name = "messenger_user")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseModel implements UserDetails {
    @Column(unique = true)
    String username;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String first_name;
    @Column(nullable = false)
    String last_name;
    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}