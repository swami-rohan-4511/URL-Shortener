package com.example.shortener.repo;

import com.example.shortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByCode(String code);
    boolean existsByCode(String code);
    List<ShortUrl> findTop100ByOrderByCreatedAtDesc();
}
