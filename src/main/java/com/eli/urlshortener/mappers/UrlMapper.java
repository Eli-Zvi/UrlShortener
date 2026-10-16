package com.eli.urlshortener.mappers;

import com.eli.urlshortener.DTOs.UrlDto;
import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.DTOs.UrlResponse;
import com.eli.urlshortener.utility.Base62;
import org.springframework.stereotype.Service;

@Service
public class UrlMapper {

    public UrlDto mapUrlToDto(Url url){
        return new UrlDto(url.getOwner(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getCreatedAt(), url.getLastClicked()
        );
    }

    public UrlResponse mapUrlToResponse(Url url){
        return new UrlResponse(url.getOwner().getUsername(),
                url.getOriginalUrl(), Base62.encode(url.getId()),
                url.getCreatedAt()
        );
    }

    public UrlResponse mapDtoToResponse(UrlDto dto){
        return new UrlResponse(dto.owner().getUsername(),
                dto.originalUrl(), dto.shortCode(),
                dto.createdAt()
        );
    }
}
