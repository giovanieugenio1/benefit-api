package com.corporate.benefits.benefit_api.mapper;

import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        return dto;
    }
}
