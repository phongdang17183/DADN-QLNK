package com.example.IotProject.service;

import org.springframework.stereotype.Service;

import com.example.IotProject.model.User;
import com.example.IotProject.repository.UserRepository;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

}
