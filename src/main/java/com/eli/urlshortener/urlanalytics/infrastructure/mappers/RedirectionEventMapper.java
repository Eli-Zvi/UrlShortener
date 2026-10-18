package com.eli.urlshortener.urlanalytics.infrastructure.mappers;


import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventDto;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import org.springframework.stereotype.Service;

@Service
public class RedirectionEventMapper {

    public RedirectionEvent mapDtoToEntity(RedirectionEventDto eventDto){
        RedirectionEvent entity = new RedirectionEvent();
        entity.setUrlId(eventDto.urlId());
        entity.setReferrer(eventDto.referrer());
        entity.setUserAgent(eventDto.userAgent());
        entity.setLastClicked(eventDto.lastClicked());
        entity.setCreatedAt(eventDto.createdAt());
        entity.setOwnerUsername(eventDto.username());

        return entity;
    }

    public RedirectionEventDto mapEntityToDto(RedirectionEvent event){
        return new RedirectionEventDto(event.getUrlId(), event.getOwnerUsername(), event.getLastClicked(),
                event.getCreatedAt(), event.getUserAgent(), event.getReferrer());
    }
}
