package com.eli.urlshortener;

import com.eli.urlshortener.models.AppUser;
import com.eli.urlshortener.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Bean
    public CommandLineRunner addUsers(UserRepository userRepository, PasswordEncoder encoder){

        return args -> {
            if (!userRepository.existsByUsername("test"))
                userRepository.save(new AppUser("test", encoder.encode("test")));
        };
    }

}
