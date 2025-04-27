package UserDomain.factory;

import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.model.User;

public interface UserFactory {
    User createUser(UserDTO userDTO);
    User updateUser(User user, UserDTO userDTO);
}
