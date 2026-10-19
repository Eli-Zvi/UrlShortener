package com.eli.urlshortener.urlanalytics.controllers;

import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlanalytics.DTOs.TotalClicksDto;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
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
    public List<RedirectionEvent> getUrlRedirectionEvents(@PathVariable Base62 shortCode){
        return analyticsService.getUrlRedirectionEvents(shortCode);
    }

    @GetMapping(value = "/totalClicks/{shortCode}")
    @ResponseStatus(HttpStatus.OK)
    public TotalClicksDto getTotalClicks(@PathVariable Base62 shortCode){
        return new TotalClicksDto(shortCode.base62Num(), analyticsService.getTotalClicks(shortCode));
    }
}
