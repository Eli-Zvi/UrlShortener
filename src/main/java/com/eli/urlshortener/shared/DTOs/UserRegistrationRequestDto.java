package com.eli.urlshortener.shared.DTOs;

import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(@Size(min = 4, max = 16) String username,
                                         @Size(min=8, max=16) String password) {
}
