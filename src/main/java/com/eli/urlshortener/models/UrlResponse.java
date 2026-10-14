package com.eli.urlshortener.models;

import com.eli.urlshortener.utility.Base62;

public record UrlResponse(String username, String originalUrl, Base62 shortCode) {
}
