package com.eli.urlshortener.urlanalytics.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * This class represents a redirection event
 *
 * @author Ilay Zvi
 */
@Entity
@Data
@Table(name = "url_redirection_analytics")
@NoArgsConstructor
public class RedirectionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="event_id_gen",
            allocationSize = 1
    )
    private Long Id;


    @Column(nullable = false)
    private String shortCode;

    @Column(name="redirected_user", nullable = false)
    private String redirectedUser;

    private Instant eventTimeStamp;
    private Instant createdAt;
    private String userAgent;
    private String referrer;
}
