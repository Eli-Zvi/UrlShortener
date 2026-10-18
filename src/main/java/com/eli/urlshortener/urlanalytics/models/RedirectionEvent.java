package com.eli.urlshortener.urlanalytics.models;

import com.eli.urlshortener.urlshortener.models.Url;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "url_redirection_analytics")
public class RedirectionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="event_id_gen",
            initialValue = 1, // Make sure shortened URL has at least 4 digits(62 ^ 3), starts at 1000
            allocationSize = 1
    )
    private Long Id;


    @Column(name="url_id", nullable = false)
    private Long urlId;
    @Column(name="owner_username", nullable = false)
    private String ownerUsername;

    private Instant lastClicked;
    private Instant createdAt;
    private String userAgent;
    private String referrer;
}
