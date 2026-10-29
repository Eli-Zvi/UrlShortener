package com.eli.urlshortener.urlshortener.infrastructure.mappers;

import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.UrlResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
