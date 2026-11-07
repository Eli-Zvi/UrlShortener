package com.eli.urlshortener.urlshortener.services;

import com.eli.urlshortener.email.EmailNotification;
import com.eli.urlshortener.email.EmailNotificationService;
import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.appuser.AppUser;
import com.eli.urlshortener.exceptions.UnsafeUrlException;
import com.eli.urlshortener.urlshortener.DTOs.UrlCreateRequestDto;
import com.eli.urlshortener.urlshortener.DTOs.UrlLookUpRequestDto;
import com.eli.urlshortener.urlshortener.UrlResponseDto;
import com.eli.urlshortener.googlesafebrowsing.SafeBrowsingClient;
import com.eli.urlshortener.googlesafebrowsing.ThreatMatchesResponse;
import com.eli.urlshortener.urlshortener.infrastructure.mappers.UrlMapper;
import com.eli.urlshortener.urlshortener.models.Url;
import com.eli.urlshortener.urlshortener.infrastructure.repositories.UrlRepository;
import com.eli.urlshortener.util.Base62;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Url Service for the UrlShortener Application
 * TODO: Separate event publishing, redirection and CRUD to separate services
 * @author Ilay Zvi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    private final SafeBrowsingClient safeBrowsingClient;
    private final EmailNotificationService emailNotificationService;

    private final ApplicationEventPublisher eventPublisher;

    private final HttpServletRequest httpServletRequest;

    @Value("${server.port}")
    private String port;
    @Value("${server.address}")
    private String address;

    private final String REDIRECTION_BASE_URL = address + ":" + port + "/api/redirect/";


    @Transactional
    // TODO: find a way to cache it while still publishing a redirection event
    //  @Cacheable(value = "REDIRECT_CACHE", key = "#urlLookUpRequestDto.code()")
    public UrlResponseDto getRedirectUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        Url redirectUrl = this.fetchUrl(urlLookUpRequestDto.code());
        return redirect(redirectUrl);
    }

    @Transactional
    public UrlResponseDto secureRedirection(UrlLookUpRequestDto urlLookUpRequestDto){
        Url redirectUrl = this.fetchUrl(urlLookUpRequestDto.code());
        ThreatMatchesResponse response = safeBrowsingClient.findThreats(redirectUrl.getOriginalUrl());

        if(response.matches() != null){
            throw new UnsafeUrlException(
                    String.format("The given shortcode: %s is unsafe. Google's Safe API has detected the following threat: %s",
                    urlLookUpRequestDto.code(), response.matches().getFirst().threatType()
            ));
        }
        return this.redirect(redirectUrl);
    }

    /**
     * Retrieves, updates, and publishes a Url
     * Retrieves a url with the given shortcode
     * Updates lastClicked
     * Retrieves redirected user, referrer and user agent
     * Saves the updated information
     * Publishes an event including the event details(Url, and the information listed above)
     * @param redirectUrl a Url to redirect to
     * @return a {@code UrlResponseDto} including information about the redirection {@code Url}
     */
    private UrlResponseDto redirect(Url redirectUrl){
        redirectUrl.setLastClicked(Instant.now());

        String redirectedUser = this.getRedirectedUser();

        String referrer = this.getReferrer();

        String userAgent = this.getUserAgent();

        urlRepository.save(redirectUrl);

        eventPublisher.publishEvent(urlMapper.UrltoUrlRedirectEvent(redirectUrl, redirectedUser, userAgent, referrer));

        return urlMapper.mapUrlToResponse(redirectUrl);
    }

    /**
     * Creates a new Url with the given information from a UrlCreateRequestDto
     * @param createRequestDto - the request dto including a new url
     * @param userDetails - {@code UserDetails} of the {@code AuthenticationPrinciple} relating to the request
     * @return a {@code UrlResponseDto} including information about the newly created {@code Url}
     */
    @CachePut(value= "URL_CACHE", key="#result.shortCode()") //add the new Url to cache
    public UrlResponseDto addUrl(UrlCreateRequestDto createRequestDto, UserDetails userDetails){
        Url newUrl = urlMapper.mapRequestToUrl(createRequestDto);
        newUrl.setOriginalUrl(normalizeUrl(newUrl.getOriginalUrl()));
        newUrl.setOwner((AppUser)userDetails);

        UrlResponseDto response = urlMapper.mapUrlToResponse(urlRepository.save(newUrl));

        emailNotificationService
                .urlCreatedNotification(new EmailNotification(
                        userDetails.getUsername(),
                        ((AppUser) userDetails).getEmail(),
                        Map.of(
                                "originalUrl", response.originalUrl(),
                                "shortUrl", REDIRECTION_BASE_URL + response.shortCode()
                        )));

        return response;
    }

    /**
     * Deletes a Url with the given shortCode
     * @param urlLookUpRequestDto a request dto including a shortcode
     * @throws ShortCodeNotFoundException if a url with the given shortCode does not exist
     */
    //TODO: Add authentication that the user trying to delete a Url is the one that created it
    @CacheEvict(value = {"URL_CACHE", "REDIRECT_CACHE"}, key = "#urlLookUpRequestDto.code()") //evict deleted URL from url cache and redirect cache
    public void deleteUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        Base62 code = urlLookUpRequestDto.code();
        Long id = code.decode();

        if(!urlRepository.existsById(id)){
            throw new ShortCodeNotFoundException(String.format("Short code %s does not exist", code));
        }

        urlRepository.deleteById(id);
    }

    /**
     * Retrieves a URL from the repository
     * @param urlLookUpRequestDto - a request dto including a shortcode
     * @return a {@code UrlResponseDto} corresponding to the request
     * @throws ShortCodeNotFoundException if a URL can't be fetched successfully with the given shortCode
     */
    @Cacheable(value = "URL_CACHE", key = "#urlLookUpRequestDto.code()")
    //Look for a cache hit and skips method if successful, otherwise add the fetched URL to cache
    public UrlResponseDto getUrl(UrlLookUpRequestDto urlLookUpRequestDto){
        return urlMapper.mapUrlToResponse(fetchUrl(urlLookUpRequestDto.code()));
    }

    /**
     * Retrieves all urls from repository TODO:paging,sorting
     * @return a list of {@code UrlResponseDtos} corresponding to the Urls in the database
     */
    public List<UrlResponseDto> getUrls(){
        return urlRepository.findAll().stream()
                .map(urlMapper::mapUrlToResponse)
                .toList();
    }


    /**
     * Generates qrcode for a given shortCode
     * @param urlLookUpRequestDto - a request dto including a shortcode
     * @return a QRCode to the link serverAddress:port/api/redirect/{@code urlLookUpRequestDto.shortCode}
     * @throws WriterException - Writer Framework error during barcode creation
     */
    public BufferedImage generateQRCodeImage(UrlLookUpRequestDto urlLookUpRequestDto) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        String serverUrl = normalizeUrl(REDIRECTION_BASE_URL + urlLookUpRequestDto.code().base62Num());
        BitMatrix bitMatrix =
                barcodeWriter.encode(serverUrl, BarcodeFormat.QR_CODE, 300, 300);
        log.info(serverUrl);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * Fetch {@code Url} from url repository by shortCode
     * @param shortCode - shortCode to fetch in the database
     * @return A {@code Url} retrieved using the shortCode
     * @throws ShortCodeNotFoundException if a URL can't be fetched successfully with the given shortCode
     */
    private Url fetchUrl(Base62 shortCode){
        Long id = shortCode.decode();

        Optional<Url> optional = urlRepository.findById(id);

        return optional.orElseThrow(() ->
                new ShortCodeNotFoundException(String.format("Short code %s does not exist", shortCode)));
    }

    /**
     * Add https if http and https are not specified in the link to make redirection seamless
     * @param originalUrl - A url to be normalized
     * @return the originalUrl if it includes http or https, otherwise an
     */
    private String normalizeUrl(String originalUrl){
        return originalUrl.startsWith("http") ? originalUrl : "http://" + originalUrl; //TODO: Change to https before deploying
    }

    /**
     * @return the referrer header's value
     */
    private String getReferrer(){
        return this.httpServletRequest.getHeader("Referer");
    }

    /**
     * future implementation using yauaa
     * @return The user agent of the request
     */
    private String getUserAgent() {
        return null;
    }

    /**
     * @return the redirect user's username or anonymousUser if the user is not authenticated
     */
    private String getRedirectedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //authentication should never be null as we call upon security context to retrieve it, except for unit tests
        //Authentication will be of type AnonymousAuthenticationToken when user is not authenticated, which will return anonymousUser
        //For edge cases, a null check is in place to return the same result as an AnonymousAuthenticationToken
        return authentication != null &&
                authentication.isAuthenticated() ? authentication.getName() : "anonymousUser";
    }
}
