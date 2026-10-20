package com.eli.urlshortener.urlanalytics.DTOs;

import com.eli.urlshortener.shared.utility.Base62;

public record RedirectionEventRequestDto(Base62 code){
}
