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
    private Long Id;

    @OneToOne
    private Url url;

    private Instant lastClicked;
    private String userAgent;
    private String referrer;
    private Long totalClicks;
}
