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

/**
 * The main security config for the application
 * @author Ilay Zvi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Initializes a PasswordEncoder bean
     * @return a new PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * The default security chain for the application
     * @param httpSecurity - the httpsecurity context
     * @return a configured SecurityFilterChain(DefaultSecurityFilterChain)
     */
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

        httpSecurity.httpBasic(Customizer.withDefaults()); //set up basic http, to be changed to JWT

        httpSecurity.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        return httpSecurity.build();
    }

    /**
     * Initializes the UserDetailsService bean for the security config
     * @param userRepository the user repository in charge of authentication
     * @return a new UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        //creates a new UserDetailsService using a lambda for the loadByUsername method
        return (username) ->
            userRepository.getAppUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));

    }
}
