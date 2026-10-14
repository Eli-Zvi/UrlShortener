package com.eli.urlshortener.controllers;

import com.eli.urlshortener.mappers.UrlMapper;
import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.models.UrlResponse;
import com.eli.urlshortener.services.UrlService;
import com.eli.urlshortener.utility.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final UrlMapper urlMapper;

    @GetMapping(value = "/redirect/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable("shortCode") String code){
        Url redirectUrl = urlService.getUrl(code);

        return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create("https://" + redirectUrl.getOriginalUrl()))
                    .build();
    }

    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK) //TODO: PAGING AND SORTING
    public List<UrlResponse> getUrls(){
        return urlService.getUrls()
                .stream()
                .map(url->
                        new UrlResponse(url.getOwner().getUsername(), url.getOriginalUrl(), Base62.encode(url.getId()))
                ).toList();
    }

    @GetMapping("/urls/{shortCode}")
    public UrlResponse getUrl(@PathVariable("shortCode") String code){
        Url url = urlService.getUrl(code);

        return urlMapper.mapUrlToResponse(url);
    }

    @DeleteMapping("/urls/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") String code){
        urlService.deleteUrl(code);
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse addUrl(@RequestBody Url url){
        Url savedUrl = urlService.addUrl(url);

        return urlMapper.mapUrlToResponse(savedUrl);
    }
}
