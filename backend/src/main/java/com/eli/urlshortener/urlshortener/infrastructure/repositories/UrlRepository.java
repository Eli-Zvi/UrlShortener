package com.eli.urlshortener.urlshortener.infrastructure.repositories;

import com.eli.urlshortener.urlshortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
