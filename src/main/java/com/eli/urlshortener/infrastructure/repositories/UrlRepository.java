package com.eli.urlshortener.infrastructure.repositories;

import com.eli.urlshortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
