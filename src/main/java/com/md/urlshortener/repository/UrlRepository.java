package com.md.urlshortener.repository;

import com.md.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    Optional<Url> findUrlByShortUrl(String urlEntity);
}
