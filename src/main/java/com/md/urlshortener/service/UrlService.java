package com.md.urlshortener.service;

import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.dto.converter.UrlDtoConverter;
import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.exception.UrlNotFoundException;
import com.md.urlshortener.model.Url;
import com.md.urlshortener.repository.UrlRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlDtoConverter urlDtoConverter;

    public UrlService(UrlRepository urlRepository, UrlDtoConverter urlDtoConverter) {
        this.urlRepository = urlRepository;
        this.urlDtoConverter = urlDtoConverter;
    }

    protected Url getShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("url not found " + shortUrl));
    }

    protected Url findUrlById(Integer id) {
        return urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("url not found " + id));
    }

    protected List<Url> getUrlList() {
        return urlRepository.findAll();
    }

    public List<UrlDto> getUrlDtoList() {
        return urlDtoConverter.convertToUrlDtoList(getUrlList());
    }

    public String createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        Url url = new Url();

        url.setShortUrl(createShortUrlRequest.getShortUrl());
        url.setLongUrl(createShortUrlRequest.getLongUrl());
        url.setCreationDate(LocalDateTime.now());

        String generator = generator();
        url.setShortUrl(generator);

        urlRepository.save(url);

        return "short url -> " + url.getShortUrl() + "\n"+"url id -> " + url.getId();
    }

    public UrlDto getUrlById(Integer id) {
        return urlDtoConverter.convertToUrlDto(findUrlById(id));
    }

    public HttpHeaders getUrl(String url) throws URISyntaxException {
        Url shortUrl = getShortUrl(url);
        URI uri = new URI(shortUrl.getLongUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return httpHeaders;
    }

    public String deleteUrlById(Integer id) {
        getUrlById(id);
        urlRepository.deleteById(id);
        return ("url deleted successfully " + id);
    }

    private String generator() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMddhhmmssSS");
        return (dateFormat.format(date));
    }

}
