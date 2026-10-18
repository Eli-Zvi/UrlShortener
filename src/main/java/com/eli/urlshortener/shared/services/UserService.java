package com.eli.urlshortener.shared.services;

import com.eli.urlshortener.shared.exceptions.UsernameAlreadyExistsException;
import com.eli.urlshortener.shared.infrastructure.UserRepository;
import com.eli.urlshortener.shared.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(AppUser appUser){
        if (userRepository.existsByUsername(appUser.getUsername())){
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists", appUser.getUsername()));
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);

        return appUser.getUsername();
    }
}
