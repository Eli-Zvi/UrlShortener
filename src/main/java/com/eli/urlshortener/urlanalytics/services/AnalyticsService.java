package com.eli.urlshortener.urlanalytics.services;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksDto;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.RedirectionEventMapper;
import com.eli.urlshortener.urlanalytics.infrastructure.repositories.RedirectionEventRepository;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final RedirectionEventRepository redirectionEventRepository;
    private final RedirectionEventMapper redirectionEventMapper;

    public List<RedirectionEvent> getUrlRedirectionEvents(Base62 shortCode){
        Long urlId = shortCode.decode();

        if (this.analyticsExistsByUrlId(urlId)){
            return redirectionEventRepository.findAllByUrlId(urlId);
        }
        //Could/should be changed to no exception and an empty body
        throw new ShortCodeNotFoundException(String.format("No analytics available for short code %s", shortCode));
    }

    public Long getTotalClicks(Base62 shortCode){
        Long urlId = shortCode.decode();

        return redirectionEventRepository.countAllByUrlId(urlId);
    }

    public void addEvent(RedirectionEventDto eventDto){
        redirectionEventRepository.save(redirectionEventMapper.mapDtoToEntity(eventDto));
    }

    private boolean analyticsExistsByUrlId(Long urlId){
        return redirectionEventRepository.existsByUrlId(urlId);
    }
}
