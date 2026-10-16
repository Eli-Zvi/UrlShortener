package com.eli.urlshortener.repositories;

import com.eli.urlshortener.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    boolean existsByUsername(String username);

    AppUser getAppUserByUsername(String username);
}
