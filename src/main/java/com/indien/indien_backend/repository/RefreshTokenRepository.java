package com.indien.indien_backend.repository;

import java.util.Optional;

import com.indien.indien_backend.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>
{
    Optional<RefreshToken> findByKey(String key);
}
