package com.example.IotProject.repository;

import com.example.IotProject.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    User findByUsernameAndEmailAndOtp(String username, String email, String otp);

    User findByUsernameAndEmail(String username, String email);

}
