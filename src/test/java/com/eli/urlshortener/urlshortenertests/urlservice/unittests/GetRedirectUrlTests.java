package com.eli.urlshortener.urlshortenertests.urlservice.unittests;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.urlshortener.UrlRedirectEvent;
import com.eli.urlshortener.urlshortener.UrlResponseDto;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapperImpl;
import com.eli.urlshortener.util.Base62;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.appuser.AppUser;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetRedirectUrlTests {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Spy
    private UrlMapperImpl urlMapper;

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
        doNothing().when(eventPublisher).publishEvent(any(UrlRedirectEvent.class));

        //method call
        UrlResponseDto result = urlService.getRedirectUrl(requestDto);

        //assertions

        verify(urlRepository, times(1)).findById(testUrl.getId()); //check that getUrl executed successfully

        assertNotNull(result.lastClicked()); //check that lastClicked was updated
        assertEquals(urlMapper.mapUrlToResponse(testUrl), result); //check that the testUrl with lastClicked updated is what was returned
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
