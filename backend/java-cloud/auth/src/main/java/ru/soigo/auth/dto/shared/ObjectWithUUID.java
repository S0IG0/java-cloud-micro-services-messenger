package ru.soigo.auth.dto.shared;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * A base class for dto that includes a UUID identifier.
 * <p>
 * This class is intended to be extended by other entity classes that require
 * a UUID as an identifier. It provides a common structure and annotations
 * for UUID handling and includes various Lombok annotations for boilerplate
 * code reduction.
 * </p>
 * <p>
 * The class is annotated with {@link MappedSuperclass}, meaning it will not
 * be mapped to a database table by itself but its properties will be inherited
 * by its subclasses.
 * </p>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@link AllArgsConstructor} - Generates a constructor with one parameter for each field in the class.</li>
 *   <li>{@link NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@link MappedSuperclass} - Specifies that this class is a superclass whose properties will be inherited by its subclasses.</li>
 *   <li>{@link SuperBuilder} - Provides a builder pattern for object creation with inheritance.</li>
 *   <li>{@link Getter} - Generates getters for all fields.</li>
 *   <li>{@link Setter} - Generates setters for all fields.</li>
 *   <li>{@link ToString} - Generates a toString method that includes all fields.</li>
 * </ul>
 *
 * <p><b>Field:</b></p>
 * <ul>
 *   <li>{@code id} - A universally unique identifier (UUID) for the entity.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * public class MyDto extends ObjectWithUUID {
 *     // additional fields and methods
 * }
 * }
 * </pre>
 *
 * @see jakarta.persistence.MappedSuperclass
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.experimental.SuperBuilder
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.ToString
 */
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@ToString
public class ObjectWithUUID {
    /**
     * A universally unique identifier (UUID) for the entity.
     */
    protected UUID id;
}
