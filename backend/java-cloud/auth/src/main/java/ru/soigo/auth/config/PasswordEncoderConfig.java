package ru.soigo.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up the PasswordEncoder bean.
 * <p>
 * This class provides a configuration for the {@link PasswordEncoder} used
 * within the application, specifically configuring it to use the
 * {@link BCryptPasswordEncoder}. BCrypt is a widely used algorithm for
 * hashing passwords securely.
 * </p>
 * <p>
 * The {@link BCryptPasswordEncoder} implements a hashing function that incorporates
 * a salt to protect against rainbow table attacks and is designed to be computationally
 * expensive to thwart brute force attacks.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 *  @Autowired
 *  private PasswordEncoder passwordEncoder;
 * }
 * </pre>
 *
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates and configures a {@link PasswordEncoder} bean.
     * <p>
     * The {@link BCryptPasswordEncoder} is instantiated and returned to be used
     * for password encoding throughout the application. This encoder is recommended
     * for securing passwords due to its strength and the fact that it incorporates
     * a salt to protect against certain types of attacks.
     * </p>
     *
     * @return a configured {@link PasswordEncoder} instance using {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
