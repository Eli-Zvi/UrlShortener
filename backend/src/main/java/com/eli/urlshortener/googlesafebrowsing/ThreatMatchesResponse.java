package com.eli.urlshortener.googlesafebrowsing;
import com.eli.urlshortener.googlesafebrowsing.ThreatOptions.*;

import java.util.List;

/**
 * This class represents a payload for the Google threatMatches.find Safe Browsing endpoint
 * See the following link for more details of the response body:
 * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/threatMatches/find#response-body">Response Body of endpoint</a>
 * For more information about the types used by ThreatMatch: {@link com.eli.urlshortener.googlesafebrowsing.ThreatOptions}
 * @param matches the ThreatMatch/s identified by Google's Safe Browsing threatMatches.find
 */
public record ThreatMatchesResponse(
        List<ThreatMatch> matches
) {

    public record ThreatMatch(
            ThreatType threatType,
            PlatformType platformType,
            ThreatEntryType threatEntryType,
            ThreatEntry threat,
            List<MetadataEntry> threatEntryMetadata,
            String cacheDuration
    ){}

    public record MetadataEntry(String key,
                                String value
    ){}
}
