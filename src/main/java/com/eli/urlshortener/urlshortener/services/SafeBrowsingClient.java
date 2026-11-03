package com.eli.urlshortener.urlshortener.services;

import java.util.Collection;

public interface SafeBrowsingClient {
    Collection<String> findThreats(String url);
}
