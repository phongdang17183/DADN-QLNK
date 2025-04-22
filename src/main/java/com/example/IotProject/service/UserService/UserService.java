package com.example.IotProject.service.UserService;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.UserModel;
import com.example.IotProject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(UserModel user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new ExistUsernameException("Username already exists!");
        }
        user.setOtp(UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new DataNotFoundException("User not found with username: " + username));
    }
    @Override
    public void updateUser(Long id,UserModel user) {
        UserModel existingUser = userRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("User not found with id: " + id));
        if (user.getUsername() !=null)
            existingUser.setUsername(user.getUsername());
        if (user.getPassword() !=null){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(encodedPassword);
        }
        if (user.getFullName() !=null)
            existingUser.setFullName(user.getFullName()); 
        if (user.getRole() !=null)
            existingUser.setRole(user.getRole());
        if (user.getEmail() !=null)
            existingUser.setEmail(user.getEmail());
        if (user.getPhone() !=null)
            existingUser.setPhone(user.getPhone());
        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserModel> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<UserModel> getUserByRole(String role) {
        return userRepository.findByRole(role);
    }

    public UserModel getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserName()).orElseThrow(() ->
            new DataNotFoundException("User not found"));
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }

    }

}
