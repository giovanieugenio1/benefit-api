package com.corporate.benefits.benefit_api.service;

import com.corporate.benefits.benefit_api.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO create(UserDTO dto);

    UserDTO update(Long id, UserDTO dto);

    void delete(Long id);

    UserDTO findById(Long id);

    List<UserDTO> findAll();
}
