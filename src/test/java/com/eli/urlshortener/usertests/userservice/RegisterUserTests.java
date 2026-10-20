package com.eli.urlshortener.usertests.userservice;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationRequestDto;
import com.eli.urlshortener.shared.exceptions.UsernameAlreadyExistsException;
import com.eli.urlshortener.appuser.infrastructure.UserRepository;
import com.eli.urlshortener.appuser.models.AppUser;
import com.eli.urlshortener.appuser.services.UserService;
import com.eli.urlshortener.shared.infrastructure.mappers.UserRegistrationMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserRegistrationMapperImpl mapper;

    @InjectMocks
    private UserService userService;

    private AppUser appUser;

    @BeforeEach
    void setUp(){
        appUser = new AppUser("test", "test");
    }

    @Test
    @DisplayName("""
            GIVEN: AppUser that exists in the database
            WHEN: registerUser is called with the AppUser
            THEN: an exception of type UsernameAlreadyExistsException is thrown
            """)
    void userExistsTest(){
        when(userRepository.existsByUsername("test")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () ->
                userService.registerUser(
                        new UserRegistrationRequestDto(
                                appUser.getUsername(), appUser.getPassword()
                                )));
    }

    @Test
    @DisplayName("""
            GIVEN: AppUser that does not exist in the database
            WHEN: registerUser is called with the AppUser
            THEN: UserRepository.save is called once
            AND: PasswordEncoder.encode is called once
            AND: the AppUser's username is returned
            """)
    void userDoesNotExistTest(){
        when(passwordEncoder.encode("test")).thenReturn("encodedTest");
        AppUser passEncodedUser = new AppUser(appUser.getUsername(), "encodedTest");
        when(userRepository.save(passEncodedUser)).thenReturn(passEncodedUser);

        String result = userService.registerUser((
                new UserRegistrationRequestDto(
                        appUser.getUsername(), appUser.getPassword()
                ))).username();

        verify(userRepository, times(1)).save(passEncodedUser);
        verify(userRepository, times(0)).save(appUser);
        verify(passwordEncoder, times(1)).encode("test");
        assertEquals(appUser.getUsername(), result);
    }
}
