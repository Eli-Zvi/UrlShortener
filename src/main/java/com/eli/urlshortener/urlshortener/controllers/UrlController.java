package com.eli.urlshortener.urlshortener.controllers;

import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.DTOs.UrlResponseDto;
import com.eli.urlshortener.urlshortener.services.UrlService;

import com.eli.urlshortener.util.Base62;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@CrossOrigin //TODO: LIMIT CROSS ORIGINS URLS LATER
public class UrlController {

    private final UrlService urlService;
    private final UrlMapper urlMapper;

    @GetMapping(value = "/redirect/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        Url redirectUrl = urlService.getRedirectUrl(requestDto);

        return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create(redirectUrl.getOriginalUrl()))
                    .build();
    }

    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK) //TODO: PAGING AND SORTING
    public List<UrlResponseDto> getUrls(){
        return urlService.getUrls();
    }

    @GetMapping("/urls/{shortCode}")
    public UrlResponseDto getUrl(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        Url url = urlService.getUrl(requestDto);

        return urlMapper.mapUrlToResponse(url);
    }

    @DeleteMapping("/urls/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        urlService.deleteUrl(requestDto);
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponseDto addUrl(@RequestBody @Valid UrlCreateRequestDto createRequestDto,
                                 @AuthenticationPrincipal UserDetails user){
        return urlService.addUrl(createRequestDto, user);
    }

    @GetMapping(value = "/qrcode/{shortCode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable("shortCode") UrlLookUpRequestDto urlLookUpRequestDto)
            throws Exception {
        return ResponseEntity.ok().body(urlService.generateQRCodeImage(urlLookUpRequestDto));
    }
}
