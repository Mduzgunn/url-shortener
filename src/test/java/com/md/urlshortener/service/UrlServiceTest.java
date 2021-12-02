package com.md.urlshortener.service;

import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.dto.converter.UrlDtoConverter;
import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.model.Url;
import com.md.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

public class UrlServiceTest {

    private UrlRepository urlRepository;
    private UrlDtoConverter urlDtoConverter;

    private UrlService urlService;

    public CreateShortUrlRequest generateShortUrl() {
        return new CreateShortUrlRequest("short_url", "long_url");
    }

    public Url generateUrl() {
        return new Url(
                99,
                "longUrl",
                ""
        );
    }

    public UrlDto generateUrlDto() {
        return new UrlDto(
                "", "", LocalDateTime.now()
        );
    }

    @BeforeEach
    void setUp() {
        urlRepository = mock(UrlRepository.class);
        urlDtoConverter = mock(UrlDtoConverter.class);

        urlService = new UrlService(urlRepository, urlDtoConverter);
    }

//    void testGetPostById_whenIdExist_itShouldReturnPostDto() {
//void testGetPostById_whenIdNotExists_itShouldThrowNotFoundException() {
//    void testGetPostList_itShouldReturnListOfPostDto() {

    @Test
    void testGetUrlById_itShouldReturnUrlDto() {
        Url url = generateUrl();

        Mockito.when(urlRepository.findByShortUrl(url.getShortUrl())).thenReturn(Optional.of(url));

        Url result = urlService.getShortUrl(url.getShortUrl());

        assertEquals(url, result);

        Mockito.verify(urlRepository).findByShortUrl(url.getShortUrl());
    }




}
