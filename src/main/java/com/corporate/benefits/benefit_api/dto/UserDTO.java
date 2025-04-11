package com.corporate.benefits.benefit_api.dto;

import com.corporate.benefits.benefit_api.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    private Role role;
}
