package com.example.IotProject.dto;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String fullName;
    private String username;
    private String password;
    private String role;
    private String gender;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
