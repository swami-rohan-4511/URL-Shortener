package com.example.shortener.web;

import com.example.shortener.model.ShortUrl;
import com.example.shortener.service.ShortenerService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShortenerController {
    private final ShortenerService service;

    public ShortenerController(ShortenerService service) { this.service = service; }

    public record ShortenRequest(@NotBlank String url, Integer expiresInDays) {}
    public record ShortenResponse(String code, String shortUrl) {}

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@RequestBody ShortenRequest req) {
        ShortUrl su = service.create(req.url(), req.expiresInDays());
        String shortUrl = "/r/" + su.getCode();
        return ResponseEntity.created(URI.create(shortUrl)).body(new ShortenResponse(su.getCode(), shortUrl));
    }

    @GetMapping("/r/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String target = service.resolve(code);
        if (target == null) return ResponseEntity.notFound().build();
        service.incrementClicks(code);
        return ResponseEntity.status(302).header("Location", target).build();
    }

    @GetMapping("/links")
    public List<ShortUrl> list() { return service.listRecent(); }

    @DeleteMapping("/links/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
