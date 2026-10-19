package com.eli.urlshortener.shared.controllers;

import com.eli.urlshortener.shared.DTOs.UserRegistrationDto;
import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.shared.services.UserService;
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
    public UserRegistrationDto registerUser(@RequestBody @Valid AppUser appUser){
        return new UserRegistrationDto(userService.registerUser(appUser));
    }
}
