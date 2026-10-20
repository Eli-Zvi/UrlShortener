package com.eli.urlshortener.urlshortener.infrastructure.mappers;

import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.DTOs.UrlResponseDto;
import com.eli.urlshortener.shared.utility.Base62;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface UrlMapper {


    Url mapRequestToUrl(UrlCreateRequestDto urlCreateRequestDto);
    /*{
        Url mappedUrl = new Url();
        mappedUrl.setOriginalUrl(urlCreateRequestDto.originalUrl());
        mappedUrl.setDescription(urlCreateRequestDto.description());
        return mappedUrl;
    } */

    UrlResponseDto mapUrlToResponse(Url url);
    /*{
        return new UrlResponseDto(url.getOwner().getUsername(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getDescription(), url.getLastClicked(), url.getCreatedAt()
        );
    }*/
}
