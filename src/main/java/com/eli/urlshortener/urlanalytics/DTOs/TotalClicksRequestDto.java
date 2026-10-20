package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.shared.utility.Base62;

public record TotalClicksRequestDto(Base62 code) {
}
