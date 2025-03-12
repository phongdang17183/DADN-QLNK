package com.example.IotProject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.User;
import com.example.IotProject.repository.UserRepository;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new ExistUsernameException("Username already exists!");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(User user) {
        if(!userRepository.existsById(user.getId())) {
            throw new DataNotFoundException("Cannot update. User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
