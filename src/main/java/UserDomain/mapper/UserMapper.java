package UserDomain.mapper;

import UserDomain.dto.UserDTO.UserDTO;
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

    public UserDTO toUserDTO (User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
