package com.eli.urlshortener.urlshortener;

import com.eli.urlshortener.util.Base62;

import java.time.Instant;

/**
 * Dto response for a request
 * @param username - the username of the user who created the url
 * @param originalUrl - the original url that was shortened
 * @param shortCode - the shortcode of the shortened url
 * @param description - description of the url
 * @param lastClicked - the time stamp of the last time the url was clicked/used for redirection
 * @param createdAt - the time stamp of the creation of the url and its shortened version
 */
public record UrlResponseDto(String username,
                             String originalUrl,
                             Base62 shortCode,
                             String description,
                             Instant lastClicked,
                             Instant createdAt) {
}
