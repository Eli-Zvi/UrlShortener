package com.eli.urlshortener.DTOs;

import com.eli.urlshortener.utility.Base62;

import java.time.Instant;

public record UrlResponse(String username, String originalUrl, Base62 shortCode, Instant createdAt) {
}
