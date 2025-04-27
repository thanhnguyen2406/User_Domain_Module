package UserDomain.factory;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.Patient;
import UserDomain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PatientFactory implements UserFactory {
    @Override
    public User createUser(UserDTO userDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);

        return Patient.builder()
                .email(userDTO.getEmail())
                .password(userDTO.isGoogleAccount()? null : passwordEncoder.encode(userDTO.getPassword()))
                .name(userDTO.getName())
                .role("PATIENT")
                .isGoogleAccount(userDTO.isGoogleAccount())
                .birthDate(userDTO.getBirthDate())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .assurance(userDTO.getAssurance())
                .build();
    }
}
