package com.eli.urlshortener.urlanalytics.infrastructure.repositories;

import com.eli.urlshortener.urlanalytics.models.RedirectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedirectionEventRepository extends JpaRepository<RedirectionEvent, Long> {
    List<RedirectionEvent> findAllByUrlId(Long urlId);

    boolean existsByUrlId(Long urlId);

    Long countAllByUrlId(Long urlId);

    Long urlId(Long urlId);
}
