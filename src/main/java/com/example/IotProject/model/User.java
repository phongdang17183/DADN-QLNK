package com.example.IotProject.model;

import java.security.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
    private String gender;
    private String phone;
    private String email;

    private Timestamp createdAt;
    private Long createdBy;
    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "id", insertable = false, updatable = false)
    Admin admin;

}
