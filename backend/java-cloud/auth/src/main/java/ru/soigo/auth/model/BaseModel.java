package ru.soigo.auth.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a base model with common fields for all entities.
 * <p>
 * This class is intended to be extended by other entity classes to inherit common properties.
 * It includes an auto-generated unique identifier, creation timestamp, and update timestamp.
 * </p>
 * <p>
 * The class is annotated with Lombok annotations to generate boilerplate code such as
 * constructors, getters, setters, and toString method. Additionally, JPA annotations are used
 * for mapping the class as a superclass and defining the fields as columns in the database.
 * </p>
 *
 * <ul>
 *   <li>{@code @MappedSuperclass} - Specifies that this class is a superclass whose mapping information
 *       is applied to its subclasses.</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with one parameter for each field in the class.</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@code @SuperBuilder} - Generates a builder for the class that includes properties from the superclass.</li>
 *   <li>{@code @ToString} - Generates a toString method that includes the class's fields.</li>
 *   <li>{@code @Getter} and {@code @Setter} - Generate getter and setter methods for all fields.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code id} - A unique identifier for the entity. It is auto-generated using UUID strategy.</li>
 *   <li>{@code createdAt} - A timestamp indicating when the entity was created. It is set automatically and is not updatable.</li>
 *   <li>{@code updatedAt} - A timestamp indicating the last time the entity was updated. It is set automatically.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * @Entity
 * public class User extends BaseModel {
 *     private String name;
 *     private String email;
 * }
 * }</pre>
 *
 * @see MappedSuperclass
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see SuperBuilder
 * @see ToString
 * @see Getter
 * @see Setter
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Getter
@Setter
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
