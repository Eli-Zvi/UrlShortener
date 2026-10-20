package com.eli.urlshortener.urlshortener;

import java.time.Instant;

public record UrlRedirectEvent(Long urlId, String username, Instant lastClicked,
                               Instant createdAt, String userAgent, String referrer) {
}
