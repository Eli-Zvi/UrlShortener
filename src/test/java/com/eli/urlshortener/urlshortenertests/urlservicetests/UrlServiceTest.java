package com.eli.urlshortener.urlshortenertests.urlservicetests;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.models.AppUser;
import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.services.UrlService;
import com.eli.urlshortener.utility.Base62;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @Test
    @DisplayName("""
            GIVEN: shortcode that does not exist in the database
            WHEN: getUrl is called with the shortcode
            THEN: url is not returned
            AND: an exception is thrown
            """)
    void test1(){
        //assumptions
        UrlRepository urlRepository = mock(UrlRepository.class);
        UrlService urlService = new UrlService(urlRepository);

        when(urlRepository.findById(238328L)).thenReturn(Optional.empty());

        //assertions and verifications
        assertThrows(
            ShortCodeNotFoundException.class, () -> urlService.getUrl(Base62.encode(238328L))
        );
    }

    @Test
    @DisplayName("""
            GIVEN: shortcode that does exist in the database
            WHEN: getUrl is called with the shortcode
            THEN: url is returned
            """)
    void test2(){
        //assumptions
        Url testUrl = new Url();
        testUrl.setOriginalUrl("test.com");
        testUrl.setOwner(new AppUser("test","test"));
        testUrl.setId(238328L);
        UrlRepository urlRepository = mock(UrlRepository.class);
        UrlService urlService = new UrlService(urlRepository);

        when(urlRepository.findById(238328L)).thenReturn(Optional.of(testUrl));

        //method call
        Url result = urlService.getUrl(Base62.encode(238328L));

        //assertions and verifications
        verify(urlRepository, times(1)).findById(238328L);
        assertEquals(result, testUrl);
    }
}
