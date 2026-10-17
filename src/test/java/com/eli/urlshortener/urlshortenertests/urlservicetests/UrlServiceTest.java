package com.eli.urlshortener.urlshortenertests.urlservicetests;

import com.eli.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.services.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UrlServiceTest {

    @Test
    @DisplayName("""
            GIVEN:
            WHEN:
            THEN:
            """)
    void test1(){
        //assumptions
        UrlRepository urlRepository = mock(UrlRepository.class);
        UrlService urlService = new UrlService(urlRepository);


        //method call

        //assertions and verifications
    }
}
