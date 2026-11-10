package com.eli.urlshortener.urlshortener.infrastructure.mappers;

import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.UrlRedirectEvent;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.UrlResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface implemented by MapStruct dependency to map a Url to DTOs
 * @author Ilay Zvi
 */
@Mapper(componentModel = "spring")
public interface UrlMapper {


    Url mapRequestToUrl(UrlCreateRequestDto urlCreateRequestDto);
    /*{
        Url mappedUrl = new Url();
        mappedUrl.setOriginalUrl(urlCreateRequestDto.originalUrl());
        mappedUrl.setDescription(urlCreateRequestDto.description());
        return mappedUrl;
    } */

    @Mapping(expression = "java(url.getOwner().getUsername())", target = "username")
    @Mapping(expression = "java(Base62.encode(url.getId()))", target = "shortCode")
    UrlResponseDto mapUrlToResponse(Url url);
    /*{
        return new UrlResponseDto(url.getOwner().getUsername(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getDescription(), url.getLastClicked(), url.getCreatedAt()
        );
    }*/

    @Mapping(expression = "java(Base62.encode(url.getId()).base62Num())", target = "shortCode")
    @Mapping(expression = "java(url.getLastClicked())", target = "eventTimeStamp")
    UrlRedirectEvent UrltoUrlRedirectEvent(Url url, String redirectedUser, String userAgent, String referrer);
}
