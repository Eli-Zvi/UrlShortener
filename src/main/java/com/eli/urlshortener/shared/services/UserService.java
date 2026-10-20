package com.eli.urlshortener.shared.services;

import com.eli.urlshortener.shared.DTOs.UserRegistrationRequestDto;
import com.eli.urlshortener.shared.DTOs.UserRegistrationResponseDto;
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

    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto registrationRequestDto){
        if (userRepository.existsByUsername(registrationRequestDto.username())){
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists",
                    registrationRequestDto.username()));
        }

        AppUser newUser = new AppUser(registrationRequestDto.username(),
                passwordEncoder.encode(registrationRequestDto.password()));

        userRepository.save(newUser);

        return new UserRegistrationResponseDto(newUser.getUsername());
    }
}
