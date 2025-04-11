package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.LoginRequest;
import com.corporate.benefits.benefit_api.dto.UserDTO;
import com.corporate.benefits.benefit_api.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user registration and login")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered and token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {
        String token = authenticationService.register(dto);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Authenticate user and return token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest login) {
        String token = authenticationService.authenticate(login.getUsername(), login.getPassword());
        return ResponseEntity.ok(token);
    }
}
