package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;

public record TotalClicksRequestDto(Base62 code) {
}
