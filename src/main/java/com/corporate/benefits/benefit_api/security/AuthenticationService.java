package com.corporate.benefits.benefit_api.security;

import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.entities.User;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.exceptions.UserAlreadyExistsException;
import com.corporate.benefits.benefit_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String register(UserDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        userRepository.save(user);

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    public String authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or passsword");
        }

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with the provided username")
        );

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
