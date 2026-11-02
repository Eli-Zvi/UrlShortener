package com.eli.urlshortener.urlshortener.controllers;

import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.UrlResponseDto;
import com.eli.urlshortener.urlshortener.services.UrlService;

import com.google.zxing.WriterException;
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

/**
 * The controller class for Url api operations
 * @author Ilay Zvi
 */

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@CrossOrigin //TODO: LIMIT CROSS ORIGINS URLS LATER
public class UrlController {

    private final UrlService urlService;

    /**
     * An API endpoint that redirects to the page that corresponds with the shortCode if it exists
     * @param requestDto - a request dto created with the given {@code PathVariable shortCode}
     * @return a {@code ResponseEntity} with a SEE_OTHER status and a redirection link to the URI corresponding to the request shortcode
     */
    @GetMapping(value = "/redirect/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        UrlResponseDto redirectUrl = urlService.getRedirectUrl(requestDto);

        return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create(redirectUrl.originalUrl()))
                    .build();
    }

    /**
     * An API endpoint to retrieve a list of urls with the information relevant to them
     * for more information {@link com.eli.urlshortener.urlshortener.UrlResponseDto}
     * @return a list of Urls from the database with relevant information about them
     */
    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK) //TODO: PAGING AND SORTING
    public List<UrlResponseDto> getUrls(){
        return urlService.getUrls();
    }

    /**
     * An API endpoint to retrieve information relevant to a url with a given shortCode
     * for more information {@link com.eli.urlshortener.urlshortener.UrlResponseDto}
     * @param requestDto - a request dto created with the given {@code PathVariable shortCode}
     * @return a Url from the database with relevant information about them
     */
    @GetMapping("/urls/{shortCode}")
    public UrlResponseDto getUrl(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        return urlService.getUrl(requestDto);
    }

    /**
     * An API endpoint to delete a url with a given shortCode
     * @param requestDto - a request dto created with the given {@code PathVariable shortCode}
     */
    @DeleteMapping("/urls/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") @Valid UrlLookUpRequestDto requestDto){
        urlService.deleteUrl(requestDto);
    }

    /**
     * An API endpoint to create a new Url with the given originalUrl and description
     * for more information {@link com.eli.urlshortener.urlshortener.UrlResponseDto}
     * @param createRequestDto - a request dto created with the given {@code RequestBody's originalUrl}, and a {@code RequestBody's description}
     * @param user - {@code UserDetails AuthenticationPrincipal} the user's Authentication details
     * @return a Url from the database with relevant information about them
     */
    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponseDto addUrl(@RequestBody @Valid UrlCreateRequestDto createRequestDto,
                                 @AuthenticationPrincipal UserDetails user){
        return urlService.addUrl(createRequestDto, user);
    }

    /**
     * An API endpoint to create a new QRCode with the given shortcode and retrieve it as an image
     * @param urlLookUpRequestDto - a request dto created with the given {@code PathVariable shortCode}
     * @return a {@code ResponseEntity} with an OK status and a QRCode directing to a redirection link with the given shortcode
     * @throws WriterException - Writer Framework error during barcode creation
     */
    @GetMapping(value = "/qrcode/{shortCode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable("shortCode") UrlLookUpRequestDto urlLookUpRequestDto)
            throws WriterException {
        return ResponseEntity.ok().body(urlService.generateQRCodeImage(urlLookUpRequestDto));
    }
}
