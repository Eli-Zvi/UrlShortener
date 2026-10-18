package com.eli.urlshortener.urlshortener.infrastructure.mappers;

import com.eli.urlshortener.urlshortener.DTOs.UrlDto;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.DTOs.UrlResponse;
import com.eli.urlshortener.shared.utility.Base62;
import org.springframework.stereotype.Service;

@Service
public class UrlMapper {

    public UrlDto mapUrlToDto(Url url){
        return new UrlDto(url.getOwner(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getDescription(), url.getLastClicked(), url.getCreatedAt()
        );
    }

    public UrlResponse mapUrlToResponse(Url url){
        return new UrlResponse(url.getOwner().getUsername(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getDescription(), url.getLastClicked(), url.getCreatedAt()
        );
    }

    public UrlResponse mapDtoToResponse(UrlDto dto){
        return new UrlResponse(dto.owner().getUsername(),
                dto.originalUrl(), dto.shortCode(),
                dto.description(), dto.lastClicked(), dto.createdAt()
        );
    }
}
