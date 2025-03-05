package com.example.IotProject.service;

import com.example.IotProject.dto.RegisterDTO;
import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.model.User;

public interface IUserService {
    String login(String username, String password) throws DataNotFoundException;

    User createUser(RegisterDTO registerDTO);
}
