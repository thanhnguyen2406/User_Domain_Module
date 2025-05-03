package UserDomain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@OnDelete(action = OnDeleteAction.CASCADE)
public class Doctor extends User{
    @Column(nullable = false, length = 255)
    String department;

    @Column(nullable = false)
    @Min(0)
    Integer experienceYears;

    @Column(nullable = false)
    String specialization;

    @Builder.Default
    @Column(nullable = false)
    boolean available = false;
}
