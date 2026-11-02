package com.eli.urlshortener.urlanalytics.DTOs;

import java.time.Instant;

/**
 * A DTO for a redirection event response
 * @param shortCode - the shortcode of the original Url of the redirection event
 * @param redirectedUser - the username of the user that was redirected
 * @param eventTimeStamp - the time stamp of the event
 * @param createdAt - the date at which the link was originally created
 * @param userAgent - the user agent used by the user
 * @param referrer - the referring website to the api request
 */
public record RedirectionEventResponseDto(String shortCode, String redirectedUser, Instant eventTimeStamp,
                                          Instant createdAt, String userAgent, String referrer) {
}
