package com.eli.urlshortener.urlshortener.DTOs;

import com.eli.urlshortener.util.Base62;
import jakarta.validation.constraints.NotNull;

/**
 * Dto for a look-up request
 * @param code - a Base62 code to look up
 */
public record UrlLookUpRequestDto(Base62 code) {

    public UrlLookUpRequestDto(@NotNull String code) {
        this(new Base62(code));
    }
}
