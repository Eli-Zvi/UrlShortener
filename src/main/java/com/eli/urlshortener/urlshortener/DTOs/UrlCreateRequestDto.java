package com.eli.urlshortener.urlshortener.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 * Dto for a creation request
 * @param originalUrl - the new Url to create and shorten
 * @param description - a description for the new Url
 */
public record UrlCreateRequestDto(@NotNull(message = "originalUrl is required")
                                  @URL(message = "originalUrl must be a URL")
                                  String originalUrl,
                                  @Size(max = 1000, message = "Description cannot exceed 1000 letters")
                                  String description) {
    public UrlCreateRequestDto{
        if (description == null){
            description = "";
        }
    }
}
