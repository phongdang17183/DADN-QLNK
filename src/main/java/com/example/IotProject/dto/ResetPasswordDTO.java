package com.example.IotProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordDTO {
    private String username;
    private String email;
    private String otp;
    private String newPassword;

    public ResetPasswordDTO(String username, String email) {
        this.username = username;
        this.email = email;
        this.otp = "";
        this.newPassword = "";
    }

    public ResetPasswordDTO(String username, String email, String otp, String newPassword) {
        this.username = username;
        this.email = email;
        this.otp = otp;
        this.newPassword = newPassword;
    }
}
