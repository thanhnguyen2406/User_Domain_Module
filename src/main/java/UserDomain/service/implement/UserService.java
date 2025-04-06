package UserDomain.service.implement;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.exception.AppException;
import UserDomain.exception.ErrorCode;
import UserDomain.mapper.UserMapper;
import UserDomain.model.User;
import UserDomain.repository.UserRepository;
import UserDomain.service.interf.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseAPI<UserDTO> getMyInfo() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            UserDTO userDTO = UserDTO.builder()
                    .email(user.get().getEmail())
                    .name(user.get().getName())
                    .build();
            return ResponseAPI.<UserDTO>builder()
                    .code(200)
                    .data(userDTO)
                    .message("My Info fetched successfully")
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
            User existingUser = userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            if(!existingUser.getEmail().equals(userDTO.getEmail())) {
                existingUser.setEmail(userDTO.getEmail());
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            existingUser.setName(userDTO.getName());
            userRepository.save(existingUser);

            UserDTO updatedUserDTO = userMapper.toUserDTO(existingUser);

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
            if (!userDTO.getEmail().endsWith("@gmail.com")) {
                throw new AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN);
            }
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
            User user = userMapper.toUser(userDTO);
            user.setRole("DOCTOR");
            userRepository.save(user);
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
    public ResponseAPI<Void> createPatient(String email, String name) {
        try {
            if (!email.endsWith("@gmail.com")) {
                throw new AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN);
            }
            if (userRepository.findByEmail(email).isPresent()) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
            User user = User.builder()
                    .email(email)
                    .name(name)
                    .role("PATIENT")
                    .isGoogleAccount(true)
                    .build();
            userRepository.save(user);
            return ResponseAPI.<Void>builder()
                    .code(200)
                    .message("Patient register successfully")
                    .build();
        } catch (AppException e) {
            return ResponseAPI.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseAPI.<Void>builder()
                    .code(500)
                    .message("Error Occurs During Register Patient: " + e.getMessage())
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
                    .message("All Users fetched successfully")
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
                    .message("All Patients fetched successfully")
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
                    .message("All Doctors fetched successfully")
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
}
