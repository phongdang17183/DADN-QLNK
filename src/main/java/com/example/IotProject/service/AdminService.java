package com.example.IotProject.service;

import com.example.IotProject.model.User;
import com.example.IotProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserRepository userRepository;

    public AdminService (UserRepository userRepository) { this.userRepository = userRepository; }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByID(Long id){
        return userRepository.findById(id);
    }

    public User updateUser(User userUpdate){
        return userRepository.findById(userUpdate.getId()).map(user -> {
            user.setUsername(userUpdate.getUsername());
            user.setEmail(userUpdate.getEmail());
            user.setPassword(userUpdate.getPassword());
            user.setRole(userUpdate.getRole());
            user.setGender(userUpdate.getGender());
            user.setPhone(userUpdate.getPhone());
            user.setCreatedAt(userUpdate.getCreatedAt());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
