package com.eli.urlshortener.urlanalytics.controllers;

import com.eli.urlshortener.shared.utility.Base62;
import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{shortCode}")
    public List<RedirectionEvent> getUrlRedirectionEvents(@PathVariable Base62 shortCode){
        return analyticsService.getUrlRedirectionEvents(shortCode);
    }

    @GetMapping("/totalClicks/{shortCode}")
    public Long getTotalClicks(@PathVariable Base62 shortCode){
        return analyticsService.getTotalClicks(shortCode);
    }
}
