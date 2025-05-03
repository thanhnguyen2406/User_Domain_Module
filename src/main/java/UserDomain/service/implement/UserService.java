package UserDomain.service.implement;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.enums.UserType;
import UserDomain.exception.AppException;
import UserDomain.exception.ErrorCode;
import UserDomain.factory.UserFactory;
import UserDomain.factory.UserFactoryProvider;
import UserDomain.mapper.UserMapper;
import UserDomain.model.Doctor;
import UserDomain.model.Patient;
import UserDomain.model.User;
import UserDomain.repository.DoctorRepository;
import UserDomain.repository.PatientRepository;
import UserDomain.repository.UserRepository;
import UserDomain.service.interf.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    UserRepository userRepository;
    PatientRepository patientRepository;
    DoctorRepository doctorRepository;
    UserMapper userMapper;
    UserFactoryProvider factoryProvider;

    @Override
    public void createUserFactory(UserDTO userDTO) {
        UserType userType = userDTO.getUserType();
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        String sanitizedUsername = userDTO.getEmail().trim();
        if (!sanitizedUsername.endsWith("@gmail.com")) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN);
        }
        UserFactory factory = factoryProvider.getFactory(userType);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported user type: " + userType);
        }
        User user = factory.createUser(userDTO);
        if (userType == UserType.PATIENT) {
            patientRepository.save((Patient) user);
        } else if (userType == UserType.DOCTOR) {
            doctorRepository.save((Doctor) user);
        } else {
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserFactory(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String sanitizedUsername = userDTO.getEmail().trim();
        if (!sanitizedUsername.endsWith("@gmail.com")) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN);
        }
        UserType userType = UserType.valueOf(existingUser.getRole());
        UserFactory factory = factoryProvider.getFactory(userType);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported user type: " + userType);
        }
        User user = factory.updateUser(existingUser, userDTO);
        if (userType == UserType.PATIENT) {
            patientRepository.save((Patient) user);
        } else if (userType == UserType.DOCTOR) {
            doctorRepository.save((Doctor) user);
        } else {
            userRepository.save(user);
        }
    }

    @Override
    public User getUserByIdFactory(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UserType userType = UserType.valueOf(existingUser.getRole());

        return switch (userType) {
            case DOCTOR -> doctorRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            case PATIENT -> patientRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            default -> existingUser;
        };
    }

    @Override
    public ResponseAPI<UserDTO> getMyInfo() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            UserDTO userDTO = userMapper.toUserDTO(user.get());
            return ResponseAPI.<UserDTO>builder()
                    .code(200)
                    .data(userDTO)
                    .message("My info fetched successfully")
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<UserDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<UserDTO>builder()
                    .code(500)
                    .message("Error Occurs During Get User Info: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<Void> updateUser(UserDTO userDTO) {
        try {
            updateUserFactory(userDTO);
            return ResponseAPI.<Void>builder()
                    .code(200)
                    .message("User updated successfully")
                    .build();

        } catch (AppException e) {
            return ResponseAPI.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<Void>builder()
                    .code(500)
                    .message("Error occurs during user update: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<Void> deleteUser(Long id) {
        try {
            userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            userRepository.deleteById(id);

            return ResponseAPI.<Void>builder()
                    .code(200)
                    .message("User deleted successfully")
                    .build();

        } catch (AppException e) {
            return ResponseAPI.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<Void>builder()
                    .code(500)
                    .message("Error occurs during user deletion: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<Void> createDoctor(UserDTO userDTO) {
        try {
            userDTO.setUserType(UserType.DOCTOR);
            createUserFactory(userDTO);
            return ResponseAPI.<Void>builder()
                    .code(200)
                    .message("Doctor created successfully")
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<Void>builder()
                    .code(500)
                    .message("Error Occurs During Created Doctor: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<UserDTO> getUserById(Long id) {
        try {
            UserDTO dto = userMapper.toUserDTO(getUserByIdFactory(id));
            return ResponseAPI.<UserDTO>builder()
                    .code(200)
                    .message("Fetched user successfully")
                    .data(dto)
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<UserDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<UserDTO>builder()
                    .code(500)
                    .message("Error Occurs During Fetching User: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<List<UserDTO>> getAllUsers(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAll(pageable);
            if (users.getTotalElements() == 0) {
                throw new AppException(ErrorCode.NO_USERS_FOUND);
            }
            List<UserDTO> userDTOS = users.getContent().stream().map(userMapper::toUserDTO).toList();
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(200)
                    .message("All users fetched successfully")
                    .data(userDTOS)
                    .currentPage(users.getNumber())
                    .totalElements(userDTOS.size())
                    .totalPages(users.getTotalPages())
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(500)
                    .message("Error Occurs During Fetch All Users: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<List<UserDTO>> getAllPatients(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAllByRole("PATIENT" ,pageable);
            if (users.getTotalElements() == 0) {
                throw new AppException(ErrorCode.NO_USERS_FOUND);
            }
            List<UserDTO> userDTOS = users.getContent().stream().map(userMapper::toUserDTO).toList();
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(200)
                    .message("All patients fetched successfully")
                    .data(userDTOS)
                    .currentPage(users.getNumber())
                    .totalElements(userDTOS.size())
                    .totalPages(users.getTotalPages())
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(500)
                    .message("Error Occurs During Fetch All Patients: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<List<UserDTO>> getAllDoctors(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAllByRole("DOCTOR" ,pageable);
            if (users.getTotalElements() == 0) {
                throw new AppException(ErrorCode.NO_USERS_FOUND);
            }
            List<UserDTO> userDTOS = users.getContent().stream().map(userMapper::toUserDTO).toList();
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(200)
                    .message("All doctors fetched successfully")
                    .data(userDTOS)
                    .currentPage(users.getNumber())
                    .totalElements(userDTOS.size())
                    .totalPages(users.getTotalPages())
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(500)
                    .message("Error Occurs During Fetch All Doctors: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseAPI<List<UserDTO>> changDoctorAvailability(Long id) {
        try {
            Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            doctor.setAvailable(!doctor.isAvailable());
            doctorRepository.save(doctor);
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(200)
                    .message("Setting doctor availability to " + doctor.isAvailable())
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<List<UserDTO>>builder()
                    .code(500)
                    .message("Error Occurs During Setting Doctor Availability: " + e.getMessage())
                    .build();
        }
    }
}
