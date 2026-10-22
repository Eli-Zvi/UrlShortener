package com.eli.urlshortener.urlanalyticstests.analyticsservice.unittests;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.util.Base62;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.infrastructure.mappers.RedirectionEventMapperImpl;
import com.eli.urlshortener.urlanalytics.infrastructure.repositories.RedirectionEventRepository;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private RedirectionEventMapperImpl redirectionEventMapper;

    @InjectMocks
    private AnalyticsService analyticsService;

    private RedirectionEventRequestDto requestDto;
    private Long urlId;

    @BeforeEach
    void setUp(){
        urlId = 238328L;
        requestDto = new RedirectionEventRequestDto(Base62.encode(238328L));
    }

    @Test
    @DisplayName("""
            GIVEN: short code that does not have any matching analytics
            WHEN: getUrlRedirectionEvents is called
            THEN: a ShortCodeNotFoundException is thrown
            """)
    void noAnalyticsExistTest(){
        when(redirectionEventRepository.existsByUrlId(urlId)).thenReturn(false);

        assertThrows(ShortCodeNotFoundException.class, () -> analyticsService.getUrlRedirectionEvents(requestDto));
    }

    @Test
    @DisplayName("""
            GIVEN: short code that does have matching analytics
            WHEN: getUrlRedirectionEvents is called
            THEN: a list of RedirectionEvents is returned
            """)
    void analyticsExistTest(){
        List<RedirectionEventResponseDto> expected = new ArrayList<>();
        when(redirectionEventRepository.existsByUrlId(urlId)).thenReturn(true);
        when(redirectionEventRepository.findAllByUrlId(urlId)).thenReturn(List.of());

        List<RedirectionEventResponseDto> result = analyticsService.getUrlRedirectionEvents(requestDto);

        verify(redirectionEventRepository, times(1)).findAllByUrlId(urlId);
        assertEquals(expected, result);
    }
}
