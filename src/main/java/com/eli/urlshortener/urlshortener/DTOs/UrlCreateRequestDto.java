package com.eli.urlshortener.urlshortener.DTOs;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UrlCreateRequestDto(@NotNull(message = "originalUrl is required")
                                  @URL(message = "originalUrl must be a URL")String originalUrl, String description) {
    public UrlCreateRequestDto{
        if (description == null){
            description = "";
        }
    }
}
