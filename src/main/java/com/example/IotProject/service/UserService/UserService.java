package com.example.IotProject.service.UserService;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.UserModel;
import com.example.IotProject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserModel addUser(UserModel user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new ExistUsernameException("Username already exists!");
        }
        return userRepository.save(user);
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
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
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
