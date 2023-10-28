package com.test.code.api;

import com.test.code.entity.User;
import com.test.code.exception.UserException;
import com.test.code.repo.UserRepo;
import com.test.code.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/oauth2/callback")
@Api(value = "OAuth2 Management")
public class OAuthApi {

    private final UserRepo userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OAuthApi(UserRepo userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = encoder;
    }

    @GetMapping
    @Operation(summary = "Generate token after oauth2 login success.", description = "This is automatically called after oauth2 login success.")
    public ResponseEntity<String> generateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

            // Check if the user is authenticated with OAuth2
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {

                // Get the user's principal (usually contains user details)
                DefaultOAuth2User user = (DefaultOAuth2User) oauth2Token.getPrincipal();

                // Access user details
                String name = user.getAttribute("name");
                String email = user.getAttribute("email");

                // check if user is in database
                Optional<User> userReference = this.userRepository.getReferenceByEmail(email);

                if (userReference.isPresent()) {
                    return returnToken(email);
                }

                // if not, create user
                User newUser = new User();
                newUser.setPassword(randomPassword());
                newUser.setEmail(email);
                newUser.setName(name);

                this.userRepository.save(newUser);

                return returnToken(email);
            }
        }

        throw new UserException("User is not authenticated");
    }

    private String randomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            stringBuilder.append(chars.charAt(index));
        }

        String randomStr = stringBuilder.toString();
        return passwordEncoder.encode(randomStr);
    }

    @NotNull
    private ResponseEntity<String> returnToken(String email) {
        String token = this.jwtTokenProvider.createToken(email);
        return ResponseEntity.ok(token);
    }
}
