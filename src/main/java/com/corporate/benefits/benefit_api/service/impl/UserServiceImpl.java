package com.corporate.benefits.benefit_api.service.impl;

import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.entities.User;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.exceptions.UserAlreadyExistsException;
import com.corporate.benefits.benefit_api.mapper.UserMapper;
import com.corporate.benefits.benefit_api.repository.UserRepository;
import com.corporate.benefits.benefit_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.corporate.benefits.benefit_api.mapper.UserMapper.toDTO;
import static com.corporate.benefits.benefit_api.mapper.UserMapper.toUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO create(UserDTO dto) {
        usernameValidation(dto);
        User user = toUser(dto);
        return toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        findById(id);
        User existing = new User();
        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());
        existing.setRole(dto.getRole());

        return toDTO(userRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with the provided id")
        );
        return toDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void usernameValidation(UserDTO dto) {
        boolean exists = userRepository.findByUsername(dto.getUsername()).isPresent();
        if (exists) {
            throw new UserAlreadyExistsException("User already exists, try to login");
        }
    }
}
