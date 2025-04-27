package UserDomain.service.interf;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.enums.UserType;
import UserDomain.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    ResponseAPI<UserDTO> getMyInfo();
    User createUser(UserType userType, UserDTO userDTO);
    ResponseAPI<Void> updateUser(UserDTO userDTO);
    ResponseAPI<Void> deleteUser(Long id);
    ResponseAPI<Void> createDoctor(UserDTO userDTO);
    ResponseAPI<List<UserDTO>> getAllUsers(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllPatients(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllDoctors(Pageable pageable);
}
