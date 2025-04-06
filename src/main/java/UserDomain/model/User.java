package UserDomain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    Boolean isGoogleAccount;
}

