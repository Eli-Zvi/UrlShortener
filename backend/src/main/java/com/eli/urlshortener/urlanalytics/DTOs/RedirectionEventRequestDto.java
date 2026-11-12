package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;


/**
 * Dto for a redirection event request
 * @param code - a Base62 code
 */
public record RedirectionEventRequestDto(Base62 code){
    public RedirectionEventRequestDto(String code) {
        this(new Base62(code));
    }
}
