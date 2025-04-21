package com.example.IotProject.service.UserService;

import java.util.List;

import com.example.IotProject.model.UserModel;

public interface IUserService {
    public List<UserModel> getAllUser();
    public UserModel getUserById(Long id);
    public void addUser(UserModel user);
    public void updateUser(Long id, UserModel user);
    public void deleteUser(Long id);
    public UserModel getCurrentUser();
    public List<UserModel> getUserByRole(String role);
    public UserModel getUserByUsername(String username);
}
