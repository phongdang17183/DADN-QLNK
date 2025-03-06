package com.example.IotProject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.dto.LoginDTO;
import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.model.User;
import com.example.IotProject.response.StringResponse;
import com.example.IotProject.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok(new StringResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterDTO registerDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        userService.createUser(registerDTO);
        return ResponseEntity.ok("User created successfully");
    }
}
