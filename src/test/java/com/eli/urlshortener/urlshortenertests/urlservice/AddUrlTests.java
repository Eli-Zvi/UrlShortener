package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddUrlTests {

    @Mock
    private UrlRepository urlRepository;

    @Spy
    private UrlMapper mapper;// = new UrlMapper();

    @InjectMocks
    private UrlService urlService;

    private AppUser appUser;

    @BeforeEach
    void setUp(){
        appUser = new AppUser("test","test");
        urlService = new UrlService(urlRepository, null, mapper);
    }

    @Test
    @DisplayName("""
            GIVEN: a normalized url beginning with https://
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url remains the same
            """)
    void normalizedUrlTest(){
        String normalizedUrl = "https://test.com";
        UrlCreateRequestDto urlCreateRequestDto = new UrlCreateRequestDto(normalizedUrl, null);

        when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> {
            Url url = invocation.getArgument(0);
            url.setId(1000L);
            return url;
        });

        urlService.addUrl(urlCreateRequestDto, appUser);

        verify(urlRepository, times(1)).save(argThat(url ->
                url.getOriginalUrl().equals(normalizedUrl) && url.getCreatedAt() != null
                && url.getOwner().equals(appUser) && url.getLastClicked() == null && url.getDescription().isEmpty()
        ));
    }

    @Test
    @DisplayName("""
            GIVEN: a normalized url beginning with http://
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url remains the same
            """)
    void normalizedUrlTest2(){
        String normalizedUrl = "https://test.com";
        UrlCreateRequestDto urlCreateRequestDto = new UrlCreateRequestDto(normalizedUrl, null);

        when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> {
            Url url = invocation.getArgument(0);
            url.setId(1000L);
            return url;
        });

        urlService.addUrl(urlCreateRequestDto, appUser);

        verify(urlRepository, times(1)).save(argThat(url ->
                url.getOriginalUrl().equals(normalizedUrl) && url.getCreatedAt() != null
                        && url.getOwner().equals(appUser) && url.getLastClicked() == null && url.getDescription().isEmpty()
        ));
    }

    @Test
    @DisplayName("""
            GIVEN: an unnormalized url
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url is normalized
            """)
    void unNormalizedUrlTest(){
        String unNormalizedUrl = "test.com";
        UrlCreateRequestDto urlCreateRequestDto = new UrlCreateRequestDto(unNormalizedUrl, null);

        when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> {
            Url url = invocation.getArgument(0);
            url.setId(1000L);
            return url;
        });

        urlService.addUrl(urlCreateRequestDto, appUser);

        verify(urlRepository, times(1)).save(argThat(url ->
                url.getOriginalUrl().equals("https://" + unNormalizedUrl) && url.getCreatedAt() != null
                        && url.getOwner().equals(appUser) && url.getLastClicked() == null && url.getDescription().isEmpty()
        ));
    }
}
