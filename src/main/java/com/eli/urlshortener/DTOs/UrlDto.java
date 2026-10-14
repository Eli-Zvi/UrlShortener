package com.eli.urlshortener.DTOs;

import com.eli.urlshortener.models.AppUser;
import com.eli.urlshortener.utility.Base62;

import java.time.Instant;

public record UrlDto(AppUser owner, String originalUrl, Base62 shortCode, Instant createdAt, Instant lastClicked) {
}
