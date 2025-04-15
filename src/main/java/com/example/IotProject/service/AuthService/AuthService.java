package com.example.IotProject.service.AuthService;

import java.util.UUID;

import com.example.IotProject.service.UserService.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.IotProject.component.JwtTokenUtil;
import com.example.IotProject.dto.EmailDTO;
import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.dto.ResetPasswordDTO;
import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.UserModel;
import com.example.IotProject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;

    @Override
    public String login(String username, String password) {
        UserModel existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Username or password is incorrect"));

        // TODO: CHECK PASSWORD BY ENCODER
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);

        // Set the authentication in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public UserModel createUser(RegisterDTO registerDTO) {

        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new ExistUsernameException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        UserModel newUser = UserModel.builder()
                .username(registerDTO.getUsername())
                .password(encodedPassword)
                .role(registerDTO.getRole())
                .gender(registerDTO.getGender())
                .phone(registerDTO.getPhone())
                .email(registerDTO.getEmail())
                .otp(UUID.randomUUID().toString().replace("-", "").substring(0, 6))
                .build();
        return userRepository.save(newUser);

    }

    @Override
    public void sendMailResetPassword(ResetPasswordDTO resetPasswordDTO) {
        UserModel existingUser = userRepository.findByUsernameAndEmail(resetPasswordDTO.getUsername(),
                resetPasswordDTO.getEmail());
        if (existingUser == null) {
            throw new DataNotFoundException("Your username or email is incorrect");
        }
        // generate otp
        existingUser.setOtp(String.valueOf((int) (Math.random() * 900000) + 100000));

        // // send mail
        // emailService.sendEmail(new EmailDTO(resetPasswordDTO.getEmail(), "Reset Password",
        //         "Your OTP is: " + existingUser.getOtp()));
        emailService.sendOTPEmail(existingUser.getFullName(), resetPasswordDTO.getEmail(), existingUser.getOtp());
        // update user
        userService.updateUser(existingUser);
    }

    @Override
    public void updatePassword(ResetPasswordDTO resetPasswordDTO) {
        UserModel existingUser = userRepository.findByUsernameAndEmailAndOtp(resetPasswordDTO.getUsername(),
                resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp());
        if (existingUser == null) {
            throw new DataNotFoundException("Your OTP is incorrect");
        }
        // set new password and remove otp
        existingUser.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        existingUser.setOtp(UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        // update user
        userService.updateUser(existingUser);

    }

}