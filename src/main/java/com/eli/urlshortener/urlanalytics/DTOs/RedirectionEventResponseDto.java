package com.eli.urlshortener.urlanalytics.DTOs;

import java.time.Instant;

public record RedirectionEventResponseDto(Long urlId, String username, Instant lastClicked,
                                          Instant createdAt, String userAgent, String referrer) {
}
