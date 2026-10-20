package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.util.Base62;

import java.time.Instant;

public record UrlResponseDto(String username, String originalUrl, Base62 shortCode,
                             String description, Instant lastClicked , Instant createdAt) {
}
