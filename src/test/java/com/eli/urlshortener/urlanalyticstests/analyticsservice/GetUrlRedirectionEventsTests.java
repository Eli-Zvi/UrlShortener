package com.eli.urlshortener.urlanalyticstests.analyticsservice;

import com.eli.urlshortener.shared.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlanalytics.infrastructure.repositories.RedirectionEventRepository;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUrlRedirectionEventsTests {

    @Mock
    private RedirectionEventRepository redirectionEventRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    private Base62 code;
    private Long urlId;

    @BeforeEach
    void setUp(){
        urlId = 238328L;
        code = Base62.encode(238328L);
    }

    @Test
    @DisplayName("""
            GIVEN: short code that does not have any matching analytics
            WHEN: getUrlRedirectionEvents is called
            THEN: a ShortCodeNotFoundException is thrown
            """)
    void noAnalyticsExistTest(){
        when(redirectionEventRepository.existsByUrlId(urlId)).thenReturn(false);

        assertThrows(ShortCodeNotFoundException.class, () -> analyticsService.getUrlRedirectionEvents(code));
    }

    @Test
    @DisplayName("""
            GIVEN: short code that does have matching analytics
            WHEN: getUrlRedirectionEvents is called
            THEN: a list of RedirectionEvents is returned
            """)
    void analyticsExistTest(){
        List<RedirectionEvent> expected = new ArrayList<>();
        when(redirectionEventRepository.existsByUrlId(urlId)).thenReturn(true);
        when(redirectionEventRepository.findAllByUrlId(urlId)).thenReturn(expected);

        List<RedirectionEvent> result = analyticsService.getUrlRedirectionEvents(code);

        verify(redirectionEventRepository, times(1)).findAllByUrlId(urlId);
        assertEquals(expected, result);
    }
}
