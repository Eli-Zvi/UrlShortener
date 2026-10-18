package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddUrlTests {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    UrlService urlService;

    private Url testUrl;
    private Base62 code;

    @BeforeEach
    void setUp(){
        testUrl = new Url();
        testUrl.setOwner(new AppUser("test","test"));
        testUrl.setId(238328L);
        code = Base62.encode(testUrl.getId());
    }

    @Test
    @DisplayName("""
            GIVEN: a normalized url beginning with https://
            WHEN: addUrl is called with the url
            THEN: A call to UrlRepository.save is made
            AND: the url remains the same
            """)
    void test1(){
        String unnormalizedUrl = "https://test.com";
        testUrl.setOriginalUrl(unnormalizedUrl);

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
    void test2(){
        String unnormalizedUrl = "test.com";
        testUrl.setOriginalUrl(unnormalizedUrl);

        Url result = urlService.addUrl(testUrl);

        verify(urlRepository, times(1)).save(testUrl);
        assertNotEquals(testUrl, result);
        assertEquals("https://" + testUrl, result.getOriginalUrl());
    }
}
