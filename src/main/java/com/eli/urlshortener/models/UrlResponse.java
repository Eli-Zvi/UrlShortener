package com.eli.urlshortener.models;

import com.eli.urlshortener.utility.Base62;

public record UrlResponse(AppUser owner, String originalUrl, Base62 shortCode) {
}
