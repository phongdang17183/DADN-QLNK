package com.example.IotProject.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "gender")
    private String gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "otp")
    private String otp;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @PrePersist
    public void PrePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RuleModel> rules;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HistoryLogModel> historyLogs;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ManagementModel> managements;
}
