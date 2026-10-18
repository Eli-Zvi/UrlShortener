package com.eli.urlshortener.urlshortener.controllers;

import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.shared.models.AppUser;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.DTOs.UrlResponse;
import com.eli.urlshortener.urlshortener.services.UrlService;

import com.eli.urlshortener.shared.utility.Base62;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@CrossOrigin //TODO: LIMIT CROSS ORIGINS URLS LATER
public class UrlController {

    private final UrlService urlService;
    private final UrlMapper urlMapper;

    @GetMapping(value = "/redirect/{shortCode}") //TODO: make a service method to update last clicked
    public ResponseEntity<Void> redirect(@PathVariable("shortCode") Base62 code){
        Url redirectUrl = urlService.getRedirectUrl(code);

        return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create(redirectUrl.getOriginalUrl()))
                    .build();
    }

    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK) //TODO: PAGING AND SORTING
    public List<UrlResponse> getUrls(){
        return urlService.getUrls()
                .stream()
                .map(urlMapper::mapUrlToResponse
                ).toList();
    }

    @GetMapping("/urls/{shortCode}")
    public UrlResponse getUrl(@PathVariable("shortCode") Base62 code){
        Url url = urlService.getUrl(code);

        return urlMapper.mapUrlToResponse(url);
    }

    @DeleteMapping("/urls/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") Base62 code){
        urlService.deleteUrl(code);
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse addUrl(@RequestBody @Valid Url url, @AuthenticationPrincipal UserDetails user){
        url.setOwner((AppUser)user);
        Url savedUrl = urlService.addUrl(url);

        return urlMapper.mapUrlToResponse(savedUrl);
    }
}
