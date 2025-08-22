package com.example.shortener.service;

import com.example.shortener.model.ShortUrl;
import com.example.shortener.repo.ShortUrlRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.List;

@Service
public class ShortenerService {
    private final ShortUrlRepository repository;
    private final Random random = new Random();

    public ShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrl create(String target, Integer expiresInDays) {
        String code = generateUniqueCode();
        ShortUrl su = new ShortUrl();
        su.setCode(code); su.setTarget(target);
        if (expiresInDays != null && expiresInDays > 0) {
            su.setExpiresAt(Instant.now().plus(Duration.ofDays(expiresInDays)));
        }
        return repository.save(su);
    }

    @Cacheable(cacheNames = "shortUrl", key = "#code")
    public String resolve(String code) {
        ShortUrl su = repository.findByCode(code).orElse(null);
        if (su == null) return null;
        if (su.getExpiresAt() != null && su.getExpiresAt().isBefore(Instant.now())) return null;
        return su.getTarget();
    }

    @CacheEvict(cacheNames = "shortUrl", key = "#code")
    public void incrementClicks(String code) {
        repository.findByCode(code).ifPresent(su -> { su.setClickCount(su.getClickCount()+1); repository.save(su); });
    }

    private String generateUniqueCode() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 7; i++) sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            String code = sb.toString();
            if (!repository.existsByCode(code)) return code;
        }
    }

    public List<ShortUrl> listRecent() {
        return repository.findTop100ByOrderByCreatedAtDesc();
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
