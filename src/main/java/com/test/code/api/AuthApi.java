package com.test.code.api;

import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.test.code.dto.UserDto;
import com.test.code.entity.User;
import com.test.code.repo.UserRepo;
import com.test.code.security.JwtTokenProvider;
import com.test.code.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@Api(value = "Authentication Management")
@Slf4j
public class AuthApi {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepo userRepository;

    private final UserService userService;

    @Autowired
    public AuthApi(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepo users, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = users;
        this.userService = userService;
    }

    @PostMapping("/signin")
    @Operation(summary = "Sign in", description = "Sign in with username or email and password")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody UserDto data) {
        log.info("Sign in");
        try {
            String email = data.getEmail(); // Assuming the usernameOrEmail field contains either username or email
            String password = data.getPassword();

            log.info("email: " + email);
            log.info("password: " + password);

            // Check if the input is an email or username
            Optional<User> userReference = this.userRepository.getReferenceByEmail(email);
            if (userReference.isEmpty()) {
                throw new BadCredentialsException("Invalid username/email or password supplied");
            }


            // Authenticate with either username or email
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            String token = jwtTokenProvider.createToken(email);

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/email or password supplied");
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up", description = "Sign up with email and password")
    public ResponseEntity<Void> signUp(@RequestBody UserDto signUpDto) {
        userService.saveUser(signUpDto);
        return ResponseEntity.status(201).build();
    }

}
