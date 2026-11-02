package com.eli.urlshortener.urlshortener;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * A record representing a url redirection event
 * @param shortCode - The shortcode of a url that was used in a redirection
 * @param redirectedUser - the username of the user that was redirected
 * @param eventTimeStamp - the time stamp of the event
 * @param createdAt - the time stamp of creation of the shortened url
 * @param userAgent - the user agent used for redirection
 * @param referrer - the website referring to the redirect address
 */
public record UrlRedirectEvent(@NotNull String shortCode,
                               String redirectedUser,
                               Instant eventTimeStamp,
                               Instant createdAt,
                               String userAgent,
                               String referrer) {
}