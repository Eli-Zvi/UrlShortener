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

/**
 * Controller for the Analytics Application
 * @author Ilay Zvi
 */

@RestController
@RequestMapping(value = "/api/analytics", consumes = "application/json")
@RequiredArgsConstructor //Inject all dependencies
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * Gets a list of redirection events for the specified shortCode
     * @param requestDto - A RedirectionEventRequestDto
     * @return a List of RedirectionEventResponseDto
     * TODO: Add pagination, sorting and filtering
     */
    @GetMapping("/{shortCode}")
    public List<RedirectionEventResponseDto> getUrlRedirectionEvents(
            @PathVariable("shortCode") @Valid RedirectionEventRequestDto requestDto){
        return analyticsService.getUrlRedirectionEvents(requestDto);
    }

    /**
     * Get the total clicks for a specified shortCode
     * @param requestDto - A TotalClicksRequestDto
     * @return  a TotalClicksResponseDto for the specific shortcode
     */
    @GetMapping(value = "/totalClicks/{shortCode}")
    @ResponseStatus(HttpStatus.OK)
    public TotalClicksResponseDto getTotalClicks(
            @PathVariable("shortCode") @Valid TotalClicksRequestDto requestDto){
        return analyticsService.getTotalClicks(requestDto);
    }
}
