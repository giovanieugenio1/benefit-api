package com.corporate.benefits.benefit_api.service.impl;

import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.entities.User;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.exceptions.UserAlreadyExistsException;
import com.corporate.benefits.benefit_api.mapper.UserMapper;
import com.corporate.benefits.benefit_api.repository.UserRepository;
import com.corporate.benefits.benefit_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.corporate.benefits.benefit_api.mapper.UserMapper.toDTO;
import static com.corporate.benefits.benefit_api.mapper.UserMapper.toUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(UserDTO dto) {
        usernameValidation(dto);
        User user = toUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        UserDTO result = toDTO(saved);

        return result;
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User existing = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with the provided id")
        );

        existing.setUsername(dto.getUsername());

        if (!dto.getPassword().equals(existing.getPassword())) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new ResourceNotFoundException("User not found")
        );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    private void usernameValidation(UserDTO dto) {
        boolean exists = userRepository.findByUsername(dto.getUsername()).isPresent();
        if (exists) {
            throw new UserAlreadyExistsException("User already exists, try to login");
        }
    }
}
