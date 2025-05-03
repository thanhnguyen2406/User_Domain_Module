package UserDomain.mapper;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.Doctor;
import UserDomain.model.Patient;
import UserDomain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();
    }

    public UserDTO toUserDTO(User user) {
        if (user instanceof Doctor doctor) {
            return UserDTO.builder()
                    .id(doctor.getId())
                    .email(doctor.getEmail())
                    .name(doctor.getName())
                    .role(doctor.getRole())
                    .isGoogleAccount(doctor.getIsGoogleAccount())
                    .department(doctor.getDepartment())
                    .experienceYears(doctor.getExperienceYears())
                    .specialization(doctor.getSpecialization())
                    .build();
        } else if (user instanceof Patient patient) {
            return UserDTO.builder()
                    .id(patient.getId())
                    .email(patient.getEmail())
                    .name(patient.getName())
                    .role(patient.getRole())
                    .isGoogleAccount(patient.getIsGoogleAccount())
                    .birthDate(patient.getBirthDate())
                    .phoneNumber(patient.getPhoneNumber())
                    .address(patient.getAddress())
                    .assurance(patient.getAssurance())
                    .build();
        } else {
            return UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole())
                    .isGoogleAccount(user.getIsGoogleAccount())
                    .build();
        }
    }

}
