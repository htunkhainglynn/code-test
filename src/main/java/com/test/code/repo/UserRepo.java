package com.test.code.repo;

import com.test.code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> getReferenceByEmail(String username);
}
