package UserDomain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@OnDelete(action = OnDeleteAction.CASCADE)
public class Patient extends User{
    @Column(nullable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;

    @Column(nullable = true)
    String phoneNumber;

    @Column(nullable = true)
    String address;

    @Column(nullable = true)
    String assurance;
}
