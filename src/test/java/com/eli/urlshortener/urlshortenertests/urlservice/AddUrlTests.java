package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddUrlTests {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    private Url testUrl;

    @BeforeEach
    void setUp(){
        testUrl = new Url();
        testUrl.setOwner(new AppUser("test","test"));
    }

    @Test
    @DisplayName("""
            GIVEN: a normalized url beginning with https://
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url remains the same
            """)
    void normalizedUrlTest(){
        String unnormalizedUrl = "https://test.com";
        testUrl.setOriginalUrl(unnormalizedUrl);

        when(urlRepository.save(testUrl)).thenReturn(testUrl);

        Url result = urlService.addUrl(testUrl);

        verify(urlRepository, times(1)).save(testUrl);
        assertEquals(testUrl, result);
    }

    @Test
    @DisplayName("""
            GIVEN: a normalized url beginning with http://
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url remains the same
            """)
    void normalizedUrlTest2(){
        String unnormalizedUrl = "https://test.com";
        testUrl.setOriginalUrl(unnormalizedUrl);

        when(urlRepository.save(testUrl)).thenReturn(testUrl);

        Url result = urlService.addUrl(testUrl);

        verify(urlRepository, times(1)).save(testUrl);
        assertEquals(testUrl, result);
    }

    @Test
    @DisplayName("""
            GIVEN: an unnormalized url
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url is normalized
            """)
    void unNormalizedUrlTest(){
        String unnormalizedUrl = "test.com";
        testUrl.setOriginalUrl(unnormalizedUrl);

        when(urlRepository.save(testUrl)).thenReturn(testUrl);

        Url result = urlService.addUrl(testUrl);

        verify(urlRepository, times(1)).save(testUrl);
        assertEquals("https://" + unnormalizedUrl, result.getOriginalUrl());
    }
}
