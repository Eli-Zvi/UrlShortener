package com.eli.urlshortener.urlanalytics.infrastructure.mappers;

import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlshortener.models.Url;

public class UrlToRedirectionEventMapper {

    public static RedirectionEventResponseDto mapUrlToEvent(Url url){
        return new RedirectionEventResponseDto(url.getId(), url.getOwner().getUsername(),
                url.getLastClicked(), url.getCreatedAt(), "temp", "temp"); // temporary place holders
    }
}
