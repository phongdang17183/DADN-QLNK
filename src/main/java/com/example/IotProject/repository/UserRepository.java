package com.example.IotProject.repository;

import com.example.IotProject.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);

    Boolean existsByUsername(String username);

    UserModel findByUsernameAndEmailAndOtp(String username, String email, String otp);

    UserModel findByUsernameAndEmail(String username, String email);

}
