package com.eli.urlshortener.urlanalytics.services;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlshortener.UrlRedirectEvent;
import com.eli.urlshortener.util.Base62;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksResponseDto;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.RedirectionEventMapper;
import com.eli.urlshortener.urlanalytics.infrastructure.repositories.RedirectionEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final RedirectionEventRepository redirectionEventRepository;
    private final RedirectionEventMapper redirectionEventMapper;

    public List<RedirectionEventResponseDto> getUrlRedirectionEvents(RedirectionEventRequestDto requestDto){
        Base62 shortCode = requestDto.code();
        Long urlId = shortCode.decode();

        if (this.analyticsExistsByUrlId(urlId)){
            return redirectionEventRepository.findAllByUrlId(urlId)
                    .stream()
                    .map(redirectionEventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        }
        //Could/should be changed to no exception and an empty body
        throw new ShortCodeNotFoundException(String.format("No analytics available for short code %s", shortCode));
    }

    public TotalClicksResponseDto getTotalClicks(TotalClicksRequestDto requestDto){
        Base62 shortCode = requestDto.code();
        Long urlId = shortCode.decode();

        return new TotalClicksResponseDto(shortCode.base62Num(), redirectionEventRepository.countAllByUrlId(urlId));
    }

    @ApplicationModuleListener
    public void addEvent(UrlRedirectEvent redirectEvent){
        RedirectionEvent event = new RedirectionEvent();
        event.setOwnerUsername(redirectEvent.username());
        event.setCreatedAt(redirectEvent.createdAt());
        event.setLastClicked(redirectEvent.lastClicked());
        event.setUserAgent(redirectEvent.userAgent());
        event.setUrlId(redirectEvent.urlId());
        event.setReferrer(redirectEvent.referrer());
        redirectionEventRepository.save(event);
    }

    private boolean analyticsExistsByUrlId(Long urlId){
        return redirectionEventRepository.existsByUrlId(urlId);
    }
}
