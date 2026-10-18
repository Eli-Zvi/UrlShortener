package com.eli.urlshortener.shared.controllers;

import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.shared.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody @Valid AppUser appUser){
        return userService.registerUser(appUser);
    }
}
