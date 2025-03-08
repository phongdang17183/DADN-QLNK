package com.example.IotProject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.dto.LoginDTO;
import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.dto.ResetPasswordDTO;
import com.example.IotProject.model.User;
import com.example.IotProject.response.StringResponse;
import com.example.IotProject.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO.getUsername(), loginDTO.getPassword());
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
        authService.createUser(registerDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> sendMailResetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        authService.sendMailResetPassword(resetPasswordDTO);
        return ResponseEntity.ok(resetPasswordDTO);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        authService.updatePassword(resetPasswordDTO);
        return ResponseEntity.ok(resetPasswordDTO);
    }

}
