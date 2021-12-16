package com.md.urlshortener;

import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.model.Url;

import java.time.LocalDateTime;
import java.util.List;

public class TestSupport {

    public Url generateUrl() {
        return new Url(
                99,
                "shortUrl",
                "longUrl"
        );
    }

    public UrlDto generateUrlDto() {
        return new UrlDto(
                "", "", LocalDateTime.now()
        );
    }

    public List<Url> generateUrlList(){
        return List.of(generateUrl());
    }

    public List<UrlDto> generateUrlDtoList(){
        return List.of(generateUrlDto());
    }

    public CreateShortUrlRequest generateCreateShortUrlRequest() {
        return new CreateShortUrlRequest(
                "shortUrl",
                "longUrl"
        );
    }
}
