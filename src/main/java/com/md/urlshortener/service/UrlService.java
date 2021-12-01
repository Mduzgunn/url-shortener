package com.md.urlshortener.service;

import com.md.urlshortener.dto.converter.UrlDtoConverter;
import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.exception.UrlNotFoundException;
import com.md.urlshortener.model.Url;
import com.md.urlshortener.repository.UrlRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlDtoConverter urlDtoConverter;

    public UrlService(UrlRepository urlRepository, UrlDtoConverter urlDtoConverter){
        this.urlRepository=urlRepository;
        this.urlDtoConverter=urlDtoConverter;
    }


    public String shortUrl(CreateShortUrlRequest createShortUrlRequest){

        Url url = new Url();

        url.setShortUrl(createShortUrlRequest.getShortUrl());
        url.setLongUrl(createShortUrlRequest.getLongUrl());

        String generator=generator();
        url.setShortUrl(generator);

        urlRepository.save(url);
        return url.getShortUrl();
    }



    protected Url getShortUrl(String shortUrl){
        return urlRepository.findUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("url bulunamadı"+shortUrl));
    }

    public HttpHeaders getUrl(String code) throws URISyntaxException {
        Url shortUrl = getShortUrl(code);
        URI uri = new URI(shortUrl.getLongUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return httpHeaders;
    }

    private String generator(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("mmddhhmmss");
        return (dateFormat.format(date));
    }


}
