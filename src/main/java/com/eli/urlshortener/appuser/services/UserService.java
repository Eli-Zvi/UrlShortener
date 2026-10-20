package com.eli.urlshortener.appuser.services;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationRequestDto;
import com.eli.urlshortener.appuser.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.shared.exceptions.UsernameAlreadyExistsException;
import com.eli.urlshortener.appuser.infrastructure.UserRepository;
import com.eli.urlshortener.appuser.models.AppUser;
import com.eli.urlshortener.appuser.infrastructure.mappers.UserRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationMapper userRegistrationMapper;

    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto registrationRequestDto){
        if (userRepository.existsByUsername(registrationRequestDto.username())){
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists",
                    registrationRequestDto.username()));
        }

        AppUser newUser = new AppUser(registrationRequestDto.username(),
                passwordEncoder.encode(registrationRequestDto.password()));

        userRepository.save(newUser);

        return userRegistrationMapper.toResponseDto(newUser);
    }
}
