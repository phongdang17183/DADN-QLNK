package com.example.IotProject.controller;

import com.example.IotProject.model.User;
import com.example.IotProject.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        return ResponseEntity.ok(adminService.addUser(user));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = adminService.getUserByID(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = adminService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}
