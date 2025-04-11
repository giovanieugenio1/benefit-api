package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.LoginRequest;
import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {
        String token = authenticationService.register(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest login) {
        String token = authenticationService.authenticate(login.getUsername(), login.getPassword());
        return ResponseEntity.ok(token);
    }
}
