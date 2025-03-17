package com.example.IotProject.service;

import java.util.List;

import com.example.IotProject.model.UserModel;

public interface IUserService {
    public List<UserModel> getAllUser();
    public UserModel getUserById(Long id);
    public UserModel addUser(UserModel user);
    public UserModel updateUser(UserModel user);
    public void deleteUser(Long id);
    public UserModel getCurrentUser();
}
