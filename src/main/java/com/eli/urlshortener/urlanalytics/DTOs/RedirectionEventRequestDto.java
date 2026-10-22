package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;
import jakarta.validation.constraints.NotNull;

public record RedirectionEventRequestDto(Base62 code){
    public RedirectionEventRequestDto(@NotNull String code) {
        this(new Base62(code));
    }
}
