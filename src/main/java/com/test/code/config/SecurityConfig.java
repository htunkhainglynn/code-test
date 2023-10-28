package com.test.code.config;

import com.test.code.security.JwtTokenFilter;
import com.test.code.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain config(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.maximumSessions(1);
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/api/books/create").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
                            .requestMatchers("/api/test").authenticated()
                            .anyRequest().permitAll()
                )
                .oauth2Login(oauth -> oauth.defaultSuccessUrl("/oauth2/callback"))
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
