package com.eli.urlshortener.urlanalytics.infrastructure.mappers;


import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import org.mapstruct.Mapper;

/**
 * Interface implemented by MapStruct dependency to map an RedirectionEvent to DTOs
 * @author Ilay Zvi
 */
@Mapper(componentModel = "spring")
public interface RedirectionEventMapper {

    //if used, may need @Mapping to map id,ownerUserName, and username
    //RedirectionEvent mapDtoToEntity(RedirectionEventResponseDto eventDto);
    /*{
        RedirectionEvent entity = new RedirectionEvent();
        entity.setUrlId(eventDto.shortCode());
        entity.setReferrer(eventDto.referrer());
        entity.setUserAgent(eventDto.userAgent());
        entity.setLastClicked(eventDto.lastClicked());
        entity.setCreatedAt(eventDto.createdAt());
        entity.setOwnerUsername(eventDto.username());

        return entity;
    } */

    RedirectionEventResponseDto mapEntityToDto(RedirectionEvent event);
    /*{
        return new RedirectionEventResponseDto(event.getUrlId(), event.getOwnerUsername(), event.getLastClicked(),
                event.getCreatedAt(), event.getUserAgent(), event.getReferrer());
    } */
}
