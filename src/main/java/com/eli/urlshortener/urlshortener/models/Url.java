package com.eli.urlshortener.urlshortener.models;


import com.eli.urlshortener.appuser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@Entity
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="url_code_seq_gen",
            initialValue = 238328, // Make sure shortened URL has at least 4 digits(62 ^ 3), starts at 1000
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    private AppUser owner;

    @NotNull(message = "originalUrl is required")
    @URL(message = "originalUrl must be a URL")
    private String originalUrl;

    private String description;

    private final Instant createdAt = Instant.now();
    private Instant lastClicked;
}
