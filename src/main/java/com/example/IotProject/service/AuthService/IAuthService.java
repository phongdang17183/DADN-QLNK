package com.example.IotProject.service.AuthService;

import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.dto.ResetPasswordDTO;
import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.model.UserModel;

public interface IAuthService {
    String login(String username, String password) throws DataNotFoundException;

    UserModel createUser(RegisterDTO registerDTO);

    void sendMailResetPassword(ResetPasswordDTO resetPasswordDTO);

    void updatePassword(ResetPasswordDTO resetPasswordDTO);
}
