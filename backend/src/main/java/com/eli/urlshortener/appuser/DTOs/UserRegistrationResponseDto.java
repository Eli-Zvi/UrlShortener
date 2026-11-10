package com.eli.urlshortener.appuser.DTOs;

/**
 * Dto of a response to a successful registration request
 * @param username - the username the user has successfully registered with
 */
public record UserRegistrationResponseDto(String username) {
}
