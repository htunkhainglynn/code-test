package com.test.code.service.impl;

import com.test.code.dto.UserDto;
import com.test.code.entity.User;
import com.test.code.exception.UserException;
import com.test.code.repo.UserRepo;
import com.test.code.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto signUpDto) {

        Optional<User> userOptional = userRepo.getReferenceByEmail(signUpDto.getEmail());

        if (userOptional.isPresent()) {
            throw new UserException("User already exists");
        }

        User user = new User(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepo.save(user);
    }
}
