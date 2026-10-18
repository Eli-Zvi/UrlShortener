package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.shared.utility.Base62;

import java.time.Instant;

public record UrlResponse(String username, String originalUrl, Base62 shortCode,
                          String description, Instant lastClicked ,Instant createdAt) {
}
