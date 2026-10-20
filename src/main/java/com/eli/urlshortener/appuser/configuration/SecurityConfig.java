package com.eli.urlshortener.appuser.configuration;

import com.eli.urlshortener.appuser.infrastructure.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests((authorize) ->{
            authorize.requestMatchers("/error", "/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
           authorize.requestMatchers("/api/redirect/**").permitAll();
           authorize.requestMatchers("/api/**").authenticated();
           authorize.requestMatchers("/h2-console/**").permitAll();
           authorize.anyRequest().denyAll();
        });

        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return (username) ->
            userRepository.getAppUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));

    }
}
