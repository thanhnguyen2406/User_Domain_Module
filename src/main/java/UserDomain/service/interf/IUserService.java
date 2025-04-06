package UserDomain.service.interf;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    ResponseAPI<Void> createPatient(String email, String name);
    ResponseAPI<UserDTO> getMyInfo();
    ResponseAPI<Void> updateUser(UserDTO userDTO);
    ResponseAPI<Void> deleteUser(Long id);
    ResponseAPI<Void> createDoctor(UserDTO userDTO);
    ResponseAPI<List<UserDTO>> getAllUsers(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllPatients(Pageable pageable);
    ResponseAPI<List<UserDTO>> getAllDoctors(Pageable pageable);
}
