package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;
import jakarta.validation.constraints.NotNull;

public record TotalClicksRequestDto(Base62 code) {

    public TotalClicksRequestDto(@NotNull String code) {
        this(new Base62(code));
    }
}
