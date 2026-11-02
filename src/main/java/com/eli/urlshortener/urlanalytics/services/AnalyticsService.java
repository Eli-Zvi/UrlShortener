package com.eli.urlshortener.urlanalytics.services;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlshortener.UrlRedirectEvent;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksResponseDto;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.RedirectionEventMapper;
import com.eli.urlshortener.urlanalytics.infrastructure.repositories.RedirectionEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Analytics Service for the Analytics Application
 * @author Ilay Zvi
 */
@Slf4j
@Service
@RequiredArgsConstructor //Inject all dependencies
public class AnalyticsService {

    private final RedirectionEventRepository redirectionEventRepository;
    private final RedirectionEventMapper redirectionEventMapper;

    /**
     * Gets a list of redirection events for a specified shortCode
     * @param requestDto - A RedirectEventRequestDto
     * @return A list of RedirectionEventResponseDto
     */
    public List<RedirectionEventResponseDto> getUrlRedirectionEvents(RedirectionEventRequestDto requestDto){
        String shortCode = requestDto.code().base62Num();

        if (redirectionEventRepository.existsByShortCode(shortCode)){
            //finds all events for an id, and maps them to a response dto
            return redirectionEventRepository
                    .findAllByShortCode(shortCode)
                    .stream()
                    .map(redirectionEventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        }

        //Could/should be changed to no exception and an empty body
        throw new ShortCodeNotFoundException(String.format("No analytics available for short code %s", shortCode));
    }

    /**
     * Gets the total clicks for a shortCode
     * @param requestDto - A TotalClicksRequestDto
     * @return A TotalClicksRequestDto
     */
    public TotalClicksResponseDto getTotalClicks(TotalClicksRequestDto requestDto){
        String shortCode = requestDto.code().base62Num();

        return new TotalClicksResponseDto(shortCode, redirectionEventRepository.countAllByShortCode(shortCode));
    }

    /**
     * Consumes an event published by the UrlService
     * @param redirectEvent - A redirect event and its information
     */
    @ApplicationModuleListener
    public void addEvent(UrlRedirectEvent redirectEvent){
        RedirectionEvent event = new RedirectionEvent();
        event.setRedirectedUser(redirectEvent.redirectedUser());
        event.setCreatedAt(redirectEvent.createdAt());
        event.setEventTimeStamp(redirectEvent.eventTimeStamp());
        event.setUserAgent(redirectEvent.userAgent());
        event.setShortCode(redirectEvent.shortCode());
        event.setReferrer(redirectEvent.referrer());
        redirectionEventRepository.save(event);
    }
}
