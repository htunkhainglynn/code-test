package com.test.code.entity;

import com.test.code.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    public User(UserDto signUpDto) {
        generateName();
        this.email = signUpDto.getEmail();
    }

    public void generateName() {
        int random = (int) (Math.random() * 100000);
        this.name = "@user" + random;
    }
}
