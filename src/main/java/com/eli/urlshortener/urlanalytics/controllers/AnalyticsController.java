package com.eli.urlshortener.urlanalytics.controllers;

import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.RedirectionEventResponseDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksRequestDto;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksResponseDto;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/analytics", consumes = "application/json")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{shortCode}")
    public List<RedirectionEventResponseDto> getUrlRedirectionEvents(@PathVariable("shortCode") @Valid RedirectionEventRequestDto requestDto){
        return analyticsService.getUrlRedirectionEvents(requestDto);
    }

    @GetMapping(value = "/totalClicks/{shortCode}")
    @ResponseStatus(HttpStatus.OK)
    public TotalClicksResponseDto getTotalClicks(@PathVariable("shortCode") @Valid TotalClicksRequestDto requestDto){
        return analyticsService.getTotalClicks(requestDto);
    }
}
