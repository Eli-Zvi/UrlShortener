package com.eli.urlshortener.appuser.controllers;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationRequestDto;
import com.eli.urlshortener.appuser.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.appuser.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/", consumes = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto registrationRequestDto){
        return userService.registerUser(registrationRequestDto);
    }
}
