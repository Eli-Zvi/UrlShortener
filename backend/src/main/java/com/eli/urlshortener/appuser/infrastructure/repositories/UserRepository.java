package com.eli.urlshortener.appuser.infrastructure.repositories;

import com.eli.urlshortener.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> getAppUserByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 then true ELSE false END " +
            "FROM AppUser u " +
            "WHERE u.username = :username " +
            "OR " +
            "u.email = :email")
    boolean existsByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}
