package UserDomain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = true)
    String password;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String role;

    @Column(nullable = false)
    @Builder.Default
    Boolean isGoogleAccount = false;
}