package com.eli.urlshortener.usertests.userservice;

import com.eli.urlshortener.shared.exceptions.UsernameAlreadyExistsException;
import com.eli.urlshortener.shared.infrastructure.UserRepository;
import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.shared.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(appUser));
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
        when(userRepository.save(appUser)).thenReturn(appUser);
        when(passwordEncoder.encode("test")).thenReturn("encodedTest");

        String result = userService.registerUser(appUser);

        verify(userRepository, times(1)).save(appUser);
        verify(passwordEncoder, times(1)).encode("test");
        assertEquals(appUser.getUsername(), result);
    }
}
