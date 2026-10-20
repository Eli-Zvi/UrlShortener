package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.util.Base62;

public record RedirectionEventRequestDto(Base62 code){
}
