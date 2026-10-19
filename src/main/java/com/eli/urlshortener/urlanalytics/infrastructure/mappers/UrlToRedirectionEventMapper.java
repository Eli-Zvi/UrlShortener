package com.eli.urlshortener.urlanalytics.infrastructure.mappers;

import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventDto;
import com.eli.urlshortener.urlshortener.models.Url;

public class UrlToRedirectionEventMapper {

    public static RedirectionEventDto mapUrlToEvent(Url url){
        return new RedirectionEventDto(url.getId(), url.getOwner().getUsername(),
                url.getLastClicked(), url.getCreatedAt(), "temp", "temp"); // temporary place holders
    }
}
