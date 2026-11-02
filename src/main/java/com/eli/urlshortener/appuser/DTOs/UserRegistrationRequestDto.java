package com.eli.urlshortener.appuser.DTOs;

import jakarta.validation.constraints.Size;

/**
 * Dto for a user registration request
 * @param username - the username to be assigned to the new registered user
 * @param password - the password to be assigned to the new registered user
 */
public record UserRegistrationRequestDto(@Size(min = 4, max = 16) String username,
                                         @Size(min=8, max=16) String password) {
}
