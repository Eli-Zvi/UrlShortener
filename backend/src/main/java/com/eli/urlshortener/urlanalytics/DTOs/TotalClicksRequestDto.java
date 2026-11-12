package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;

/**
 * Dto for a total clicks request
 * @param code - a shortCode for a url
 */
public record TotalClicksRequestDto(Base62 code) {

    public TotalClicksRequestDto(String code) {
        this(new Base62(code));
    }
}
