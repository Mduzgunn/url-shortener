package com.md.urlshortener.controller;

import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class UrlController {

    private final UrlService urlService;

    public UrlController( UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> makeRedirect(@PathVariable String shortUrl) throws URISyntaxException {
        return new ResponseEntity<>(urlService.getUrl(shortUrl), HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/short")
    public ResponseEntity<?> shortUrl(@RequestBody CreateShortUrlRequest request){
        return ResponseEntity.ok(urlService.shortUrl(request));
    }

}
