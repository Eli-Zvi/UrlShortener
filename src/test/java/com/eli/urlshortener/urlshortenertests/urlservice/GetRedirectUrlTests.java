package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.UrlToRedirectionEventMapper;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.appuser.models.AppUser;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetRedirectUrlTests {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private UrlService urlService;

    private Url testUrl;
    private UrlLookUpRequestDto requestDto;

    @BeforeEach
    void setUp(){
        testUrl = new Url();
        testUrl.setOriginalUrl("testurl.com");
        testUrl.setOwner(new AppUser("test","test"));
        testUrl.setId(238328L);
        requestDto = new UrlLookUpRequestDto(Base62.encode(testUrl.getId()));
    }

    @Test
    @DisplayName("""
            GIVEN: shortcode that does exist in the database
            WHEN: getRedirectUrl is called with the shortcode
            THEN: url is returned and lastClicked is updated
            """)
    void shortCodeExistsTest(){
        //assumptions
        when(urlRepository.findById(testUrl.getId())).thenReturn(Optional.of(testUrl));
        //method call
        Url result = urlService.getRedirectUrl(requestDto);

        //assertions

        verify(urlRepository, times(1)).findById(testUrl.getId()); //check that getUrl executed successfully
        verify(analyticsService, times(1)).addEvent(UrlToRedirectionEventMapper.mapUrlToEvent(testUrl));
        assertNotNull(result.getLastClicked()); //check that lastClicked was updated
        assertEquals(testUrl, result); //check that the testUrl with lastClicked updated is what was returned
    }

    @Test
    @DisplayName("""
            GIVEN: shortcode that does not exist in the database
            WHEN: getRedirectUrl is called with the shortcode
            THEN: a ShortCodeNotFoundException is thrown
            """)
    void shortCodeDoesNotExistTest() {
        //assumptions
        when(urlRepository.findById(testUrl.getId())).thenReturn(Optional.empty());

        //assertions
        assertThrows(ShortCodeNotFoundException.class, () -> urlService.getRedirectUrl(requestDto));
    }
}
