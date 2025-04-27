package UserDomain.factory;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.Doctor;
import UserDomain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DoctorFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);

    @Override
    public User createUser(UserDTO userDTO) {
        return Doctor.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .name(userDTO.getName())
                .role("DOCTOR")
                .isGoogleAccount(false)
                .department(userDTO.getDepartment())
                .experienceYears(userDTO.getExperienceYears())
                .specialization(userDTO.getSpecialization())
                .build();
    }

    @Override
    public User updateUser(User user, UserDTO userDTO) {
        Doctor doctor = (Doctor) user;
        doctor.setEmail(userDTO.getEmail());
        doctor.setName(userDTO.getName());
        doctor.setDepartment(userDTO.getDepartment());
        doctor.setExperienceYears(userDTO.getExperienceYears());
        doctor.setSpecialization(userDTO.getSpecialization());

        if (userDTO.getPassword() != null) {
            doctor.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return doctor;
    }
}
