package com.eli.urlshortener.urlanalytics.infrastructure.repositories;

import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectionEventRepository extends JpaRepository<RedirectionEvent, Long> {
}
