package com.eli.urlshortener.appuser.services;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationRequestDto;
import com.eli.urlshortener.appuser.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.email.EmailNotificationService;
import com.eli.urlshortener.email.EmailNotification;
import com.eli.urlshortener.exceptions.UsernameEmailAlreadyExistsException;
import com.eli.urlshortener.appuser.infrastructure.repositories.UserRepository;
import com.eli.urlshortener.appuser.AppUser;
import com.eli.urlshortener.appuser.infrastructure.mappers.UserRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Service for user credential operations
 * @author Ilay Zvi
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRegistrationMapper userRegistrationMapper;
    private final EmailNotificationService emailNotificationService;

    /**
     * Processes user registration requests
     * @param registrationRequestDto - a Registration Request
     * @return a UserRegistrationResponseDto
     * @throws UsernameEmailAlreadyExistsException if the user exists
     */
    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto registrationRequestDto){
        if (userRepository.existsByUsernameOrEmail(registrationRequestDto.username(), registrationRequestDto.email())){
            throw new UsernameEmailAlreadyExistsException(String.format("Username %s or Email %s already exists",
                    registrationRequestDto.username(), registrationRequestDto.email()));
        }

        AppUser newUser = new AppUser(registrationRequestDto.username(),
                passwordEncoder.encode(registrationRequestDto.password()),
                registrationRequestDto.email());

        userRepository.save(newUser);

        emailNotificationService.accCreatedNotification(
                new EmailNotification(
                        registrationRequestDto.username(),
                        registrationRequestDto.email(),
                        null
                )
        );

        return userRegistrationMapper.toResponseDto(newUser);
    }
}
