package UserDomain.service.interf;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    void createUserFactory(UserDTO userDTO);
    void updateUserFactory(UserDTO userDTO);
    User getUserByIdFactory(long id);

    ResponseAPI<UserDTO> getMyInfo();
    ResponseAPI<Void> updateUser(UserDTO userDTO);
    ResponseAPI<Void> deleteUser(Long id);
    ResponseAPI<Void> createDoctor(UserDTO userDTO);
    ResponseAPI<UserDTO> getUserById(Long id);
    ResponseAPI<List<UserDTO>> getAllUsers(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllPatients(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllDoctors(Pageable pageable);
    ResponseAPI<List<UserDTO>> changDoctorAvailability(Long id);
}
