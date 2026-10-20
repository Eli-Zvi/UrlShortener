package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.services.UrlService;
import com.eli.urlshortener.shared.utility.Base62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUrlTests {

    @Mock
    private UrlRepository urlRepository;

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
            GIVEN: shortcode that does not exist in the database
            WHEN: getUrl is called with the shortcode
            THEN: url is not returned
            AND: an exception is thrown
            """)
    void shortCodeDoesNotExistTest(){
        //assumptions
        when(urlRepository.findById(testUrl.getId())).thenReturn(Optional.empty());

        //assertions and verifications
        assertThrows(
            ShortCodeNotFoundException.class, () -> urlService.getUrl(requestDto)
        );
    }

    @Test
    @DisplayName("""
            GIVEN: shortcode that does exist in the database
            WHEN: getUrl is called with the shortcode
            THEN: url is returned
            """)
    void shortCodeExistsTest(){
        //assumptions
        when(urlRepository.findById(testUrl.getId())).thenReturn(Optional.of(testUrl));

        //method call
        Url result = urlService.getUrl(requestDto);

        //assertions and verifications
        verify(urlRepository, times(1)).findById(testUrl.getId());
        assertEquals(result, testUrl);
    }
}
