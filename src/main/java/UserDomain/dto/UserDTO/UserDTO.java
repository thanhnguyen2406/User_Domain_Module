package UserDomain.dto.UserDTO;

import UserDomain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    long id;
    String email;
    String password;
    String name;
    String role;
    boolean isGoogleAccount;

    //Doctor
    String department;
    Integer experienceYears;
    String specialization;

    //Patient
    LocalDate birthDate;
    String phoneNumber;
    String address;
    String assurance;

    UserType userType;
}
