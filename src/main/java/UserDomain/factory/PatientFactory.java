package UserDomain.factory;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.Patient;
import UserDomain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PatientFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
    @Override
    public User createUser(UserDTO userDTO) {
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

    @Override
    public User updateUser(User user, UserDTO userDTO) {
        Patient patient = (Patient) user;
        patient.setName(userDTO.getName());
        patient.setBirthDate(userDTO.getBirthDate());
        patient.setPhoneNumber(userDTO.getPhoneNumber());
        patient.setAddress(userDTO.getAddress());
        patient.setAssurance(userDTO.getAssurance());
        if (!user.getIsGoogleAccount()) {
            patient.setEmail(userDTO.getEmail());
            if (userDTO.getPassword() != null) {
                patient.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
        }
        return patient;
    }
}
