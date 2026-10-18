package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.shared.utility.Base62;

import java.time.Instant;

public record UrlDto(AppUser owner, String originalUrl, Base62 shortCode,
                     String description, Instant lastClicked, Instant createdAt) {
}
