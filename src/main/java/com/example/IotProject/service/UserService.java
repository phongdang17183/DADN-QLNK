package com.example.IotProject.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.IotProject.component.JwtTokenUtil;
import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.User;
import com.example.IotProject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Username or password is incorrect"));

        // TODO: CHECK PASSWORD BY ENCODER
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User createUser(RegisterDTO registerDTO) {

        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new ExistUsernameException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        User newUser = User.builder()
                .username(registerDTO.getUsername())
                .password(encodedPassword)
                .role(registerDTO.getRole())
                .gender(registerDTO.getGender())
                .phone(registerDTO.getPhone())
                .email(registerDTO.getEmail())
                .createdBy(registerDTO.getCreatedBy())
                .createdAt(registerDTO.getCreatedAt())
                .build();
        return userRepository.save(newUser);

    }

}