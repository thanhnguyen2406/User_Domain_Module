package UserDomain.factory;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.Doctor;
import UserDomain.model.User;
import org.springframework.stereotype.Component;

@Component
public class DoctorFactory implements UserFactory {
    @Override
    public User createUser(UserDTO userDTO) {
        return Doctor.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .role("DOCTOR")
                .isGoogleAccount(false)
                .department(userDTO.getDepartment())
                .experienceYears(userDTO.getExperienceYears())
                .specialization(userDTO.getSpecialization())
                .build();
    }
}
