package com.eli.urlshortener.urlshortener.models;


import com.eli.urlshortener.appuser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

/**
 * This class represents a Url to be stored in the database with its primary key correlating with a Base62 number
 * starting from 238328(1000 in Base62) and onwards
 * @author Ilay Zvi
 */
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

    @Size(max = 1000, message = "Description cannot exceed 1000 letters") //better than @Length from hibernate to decouple from hibernate
    private String description;

    private final Instant createdAt = Instant.now();
    private Instant lastClicked;
}
