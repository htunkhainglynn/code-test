package com.test.code.api;

import com.test.code.entity.User;
import com.test.code.exception.UserException;
import com.test.code.repo.UserRepo;
import com.test.code.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oauth2/callback")
@Slf4j
public class OAuthApi {

    private final UserRepo userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OAuthApi(UserRepo userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public ResponseEntity<String> generateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication: " + authentication);

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("User is authenticated");

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
                newUser.setEmail(email);
                newUser.setName(name);

                this.userRepository.save(newUser);

                return returnToken(email);
            }
        }

        throw new UserException("User is not authenticated");
    }

    @NotNull
    private ResponseEntity<String> returnToken(String email) {
        String token = this.jwtTokenProvider.createToken(email);
        return ResponseEntity.ok(token);
    }
}
