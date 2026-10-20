package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.util.Base62;

public record UrlLookUpRequestDto(Base62 code) {

    public UrlLookUpRequestDto(String code) {
        this(new Base62(code));
    }
}
