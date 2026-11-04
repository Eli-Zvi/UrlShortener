package com.eli.urlshortener.urlshortener.googlesafebrowsing;
import com.eli.urlshortener.urlshortener.googlesafebrowsing.ThreatOptions.*;

import java.util.List;

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
