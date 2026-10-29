package com.eli.urlshortener.urlanalytics.infrastructure.mappers;


import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface RedirectionEventMapper {

    //if used, may need @Mapping to map id,ownerUserName, and username
    //RedirectionEvent mapDtoToEntity(RedirectionEventResponseDto eventDto);
    /*{
        RedirectionEvent entity = new RedirectionEvent();
        entity.setUrlId(eventDto.urlId());
        entity.setReferrer(eventDto.referrer());
        entity.setUserAgent(eventDto.userAgent());
        entity.setLastClicked(eventDto.lastClicked());
        entity.setCreatedAt(eventDto.createdAt());
        entity.setOwnerUsername(eventDto.username());

        return entity;
    } */

    @Mapping(source = "ownerUsername", target = "username")
    RedirectionEventResponseDto mapEntityToDto(RedirectionEvent event);
    /*{
        return new RedirectionEventResponseDto(event.getUrlId(), event.getOwnerUsername(), event.getLastClicked(),
                event.getCreatedAt(), event.getUserAgent(), event.getReferrer());
    } */
}
