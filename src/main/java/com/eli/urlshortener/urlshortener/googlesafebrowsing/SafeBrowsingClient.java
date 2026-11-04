package com.eli.urlshortener.urlshortener.googlesafebrowsing;

/**
 * Interface contract for Google's Safe Browsing API
 *
 * @author Ilay Zvi
 */
public interface SafeBrowsingClient {
    /**
     * Defines a contract for finding threats using Google's Safe Browsing API
     * @param url a url to check
     * @return {@link com.eli.urlshortener.urlshortener.googlesafebrowsing.ThreatMatchesResponse}
     * a response from the API
     */
    ThreatMatchesResponse findThreats(String url);
}
