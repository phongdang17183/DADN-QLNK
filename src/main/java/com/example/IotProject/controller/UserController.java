package com.example.IotProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.model.UserModel;
import com.example.IotProject.response.UserResponse;
import com.example.IotProject.service.UserService.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/getUser")
    public ResponseEntity<UserResponse> getUser() {
        UserModel userModel = userService.getCurrentUser();
        UserResponse user = new UserResponse();
        user.setId(userModel.getId());
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setFullName(userModel.getFullName());
        user.setPhone(userModel.getPhone());
        user.setRole(userModel.getRole());
        user.setCreatedAt(userModel.getCreatedAt());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        userService.updateUser(id,user);
        return ResponseEntity.ok("User updated successfully!");
    }
    
}
