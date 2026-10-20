package com.eli.urlshortener.urlshortenertests.urlservice;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.appuser.models.AppUser;
import com.eli.urlshortener.util.Base62;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUrlTests {

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
            GIVEN:short code that does exist in the database
            WHEN:deleteUrl is called with the short code
            THEN:repository deletes the url
            """)
    void shortCodeExistsTest(){
        when(urlRepository.existsById(testUrl.getId())).thenReturn(true);

        urlService.deleteUrl(requestDto);

        verify(urlRepository, times(1)).deleteById(testUrl.getId());
    }

    @Test
    @DisplayName("""
            GIVEN:short code that does not exist in the database
            WHEN:delete url is called with the short code
            THEN:ShortCodeNotFoundException is thrown
            AND:repository does not get a request to delete the url
            """)
    void shortCodeDoesNotExistTest(){
        when(urlRepository.existsById(testUrl.getId())).thenReturn(false);

        assertThrows(ShortCodeNotFoundException.class, () -> urlService.deleteUrl(requestDto));
        verify(urlRepository, times(0)).deleteById(testUrl.getId());
    }
}
