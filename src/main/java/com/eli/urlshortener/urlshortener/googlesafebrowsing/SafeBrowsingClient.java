package com.eli.urlshortener.urlshortener.googlesafebrowsing;

public interface SafeBrowsingClient {
    ThreatMatchesResponse findThreats(String url);
}
