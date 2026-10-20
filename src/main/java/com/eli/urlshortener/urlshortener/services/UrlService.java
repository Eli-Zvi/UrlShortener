package com.eli.urlshortener.urlshortener.services;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.appuser.models.AppUser;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.UrlToRedirectionEventMapper;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.DTOs.UrlResponseDto;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.shared.utility.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UrlMapper urlMapper;

    @Transactional
    public Url getRedirectUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        Url target = this.getUrl(urlLookUpRequestDto);

        target.setLastClicked(Instant.now());
        urlRepository.save(target);

        analyticsService.addEvent(UrlToRedirectionEventMapper.mapUrlToEvent(target));

        return target;
    }

    public UrlResponseDto addUrl(UrlCreateRequestDto createRequestDto, UserDetails userDetails){
        Url newUrl = urlMapper.mapRequestToUrl(createRequestDto);
        newUrl.setOriginalUrl(normalizeUrl(newUrl.getOriginalUrl()));
        newUrl.setOwner((AppUser)userDetails);

        return urlMapper.mapUrlToResponse(urlRepository.save(newUrl));
    }

    public void deleteUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        Base62 code = urlLookUpRequestDto.code();
        Long id = code.decode();

        if(!urlRepository.existsById(id)){
            throw new ShortCodeNotFoundException(String.format("Short code %s does not exist", code));
        }

        urlRepository.deleteById(id);
    }

    public Url getUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        Base62 code = urlLookUpRequestDto.code();
        Long id = code.decode();

        Optional<Url> optional = urlRepository.findById(id);

        return optional.orElseThrow(() ->
                new ShortCodeNotFoundException(String.format("Short code %s does not exist", code)));
    }

    public List<UrlResponseDto> getUrls(){
        return urlRepository.findAll().stream()
                .map(urlMapper::mapUrlToResponse
                ).toList();
    }

    /*
    Add https if http and https are not specified in the link to make redirection seamless
     */
    private String normalizeUrl(String originalUrl){
        return originalUrl.startsWith("http") ? originalUrl : "https://" + originalUrl;
    }
}
