package com.eli.urlshortener.shared.infrastructure;

import com.eli.urlshortener.shared.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    boolean existsByUsername(String username);

    Optional<AppUser> getAppUserByUsername(String username);
}
