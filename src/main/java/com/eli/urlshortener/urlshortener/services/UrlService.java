package com.eli.urlshortener.urlshortener.services;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventDto;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.shared.utility.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final AnalyticsService analyticsService;

    @Transactional
    public Url getRedirectUrl(Base62 code){
        Url target = this.getUrl(code);

        target.setLastClicked(Instant.now());
        urlRepository.save(target);

        RedirectionEvent event = new RedirectionEvent();


        analyticsService.addEvent(new RedirectionEventDto(target.getId(), target.getOwner().getUsername(),
                target.getLastClicked(), target.getCreatedAt(), "temp", "temp"));

        return target;
    }

    public Url addUrl(Url url){
        url.setOriginalUrl(normalizeUrl(url.getOriginalUrl()));

        return urlRepository.save(url);
    }

    public void deleteUrl(Base62 code){
        Long id = code.decode();

        if(!urlRepository.existsById(id)){
            throw new ShortCodeNotFoundException(String.format("Short code %s does not exist", code));
        }

        urlRepository.deleteById(id);
    }

    public Url getUrl(Base62 code){
        Long id = code.decode();

        Optional<Url> optional = urlRepository.findById(id);

        return optional.orElseThrow(() ->
                new ShortCodeNotFoundException(String.format("Short code %s does not exist", code)));
    }

    public List<Url> getUrls(){
        return urlRepository.findAll();
    }

    /*
    Add https if http and https are not specified in the link to make redirection seamless
     */
    private String normalizeUrl(String originalUrl){
        return originalUrl.startsWith("http") ? originalUrl : "https://" + originalUrl;
    }
}
