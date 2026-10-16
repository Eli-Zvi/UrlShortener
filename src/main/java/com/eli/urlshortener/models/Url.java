package com.eli.urlshortener.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="url_code_seq_gen",
            initialValue = 238328 // Make sure shortened URL has at least 4 digits(62 ^ 3), starts at 1000
    )
    private Long id;

    @ManyToOne
    private AppUser owner;

    @NotNull
    private String originalUrl;

    private final Instant createdAt = Instant.now();
    private Instant lastClicked;
}
