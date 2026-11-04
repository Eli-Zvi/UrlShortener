package com.eli.urlshortener.urlshortener.googlesafebrowsing;
import java.util.List;

/**
 * This class represents a payload for the Google threatMatches.find Safe Browsing endpoint
 * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/threatMatches/find#request-body">Request Body of endpoint</a>
 * @param client - the client portion of the payload
 * @param threatInfo - the threat info portion of the payload
 */
public record ThreatMatchesRequest(Client client,
                                   ThreatInfo threatInfo) {

    public record Client(String clientId,
                         String clientVersion
    ){}

    public record ThreatInfo(List<ThreatOptions.ThreatType> threatTypes,
                             List<ThreatOptions.PlatformType> platformTypes,
                             List<ThreatOptions.ThreatEntryType> threatEntryTypes,
                             List<ThreatOptions.ThreatEntry> threatEntries
                             ){}
}
