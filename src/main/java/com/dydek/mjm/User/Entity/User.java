package com.dydek.mjm.User.Entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private UUID userid;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    private String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
