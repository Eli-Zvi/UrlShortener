package com.eli.urlshortener.urlshortener.googlesafebrowsing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.eli.urlshortener.urlshortener.googlesafebrowsing.ThreatOptions.*;

import java.net.URI;
import java.util.List;

/**
 * Safe Browsing Contract implementation
 *
 */

@Slf4j
@Service
public class SafeBrowsingClientImpl implements SafeBrowsingClient {

    @Value("${SAFE_BROWSING_API_KEY}")
    private String API_KEY;
    @Value("${spring.application.name}")
    private String CLIENT_NAME;
    @Value("${spring.application.version}")
    private String VERSION;

    private final RestClient restClient;
    private final URI SAFE_BROWSING_URI = URI.create("https://safebrowsing.googleapis.com/v4/threatMatches:find");

    /**
     * Use spring autoconfiguration for RestClient
     * @param builder autoconfigured RestClient.Builder
     */
    public SafeBrowsingClientImpl(RestClient.Builder builder){
        restClient = builder.build();
    }

    /**
     * Crafts a request and sends to google's safe browsing threatMatches.find API.
     * Retrieves the response and returns it.
     * @param url a url to check
     * @return the response from the server
     * TODO: Add caching using the caching information given by Google's Safe Browsing
     */
    @Override
    public ThreatMatchesResponse findThreats(String url) {
        final URI uri = UriComponentsBuilder
                .fromUri(SAFE_BROWSING_URI)
                .queryParam("key", API_KEY)
                .build()
                .toUri();

        ThreatMatchesRequest.Client client = new ThreatMatchesRequest.Client(CLIENT_NAME, VERSION);
        ThreatMatchesRequest.ThreatInfo threatInfo = new ThreatMatchesRequest.ThreatInfo(
                List.of(
                        ThreatType.MALWARE
                ),
                List.of(PlatformType.WINDOWS),
                List.of(ThreatEntryType.URL),
                List.of(
                        new ThreatEntry(url)
                )
        );

        ThreatMatchesResponse apiResult = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ThreatMatchesRequest(client, threatInfo)
                )
                .retrieve()
                .body(ThreatMatchesResponse.class);

        log.info(String.valueOf(apiResult));

        return apiResult;
    }
}
