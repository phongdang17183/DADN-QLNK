package com.example.IotProject.service;

import java.util.List;

import com.example.IotProject.model.User;

public interface IUserService {
    public List<User> getAllUser();
    public User getUserById(Long id);
    public User addUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long id);
}
