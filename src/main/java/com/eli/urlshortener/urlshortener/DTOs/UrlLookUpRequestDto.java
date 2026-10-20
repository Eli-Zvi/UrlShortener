package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.shared.utility.Base62;

public record UrlLookUpRequestDto(Base62 code) {
}
