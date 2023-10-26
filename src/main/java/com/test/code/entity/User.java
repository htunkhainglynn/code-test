package com.test.code.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Comment> comments;

    public User(String name, String email) {
        this.name = (name != null && !name.isEmpty()) ? name : generateDefaultName(email);
        this.email = email;
    }

    private String generateDefaultName(String email) {
        // You can implement a logic to generate a default name from the user's email or any other criteria
        // For example, you can use the first part of the email address
        String[] emailParts = email.split("@");
        return "User_" + emailParts[0];
    }
}

