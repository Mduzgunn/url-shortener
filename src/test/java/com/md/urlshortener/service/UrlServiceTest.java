package com.md.urlshortener.service;

import com.md.urlshortener.TestSupport;
import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.dto.converter.UrlDtoConverter;
import com.md.urlshortener.dto.request.CreateShortUrlRequest;
import com.md.urlshortener.exception.UrlNotFoundException;
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
import java.util.List;
import java.util.Optional;

public class UrlServiceTest extends TestSupport {

    private UrlRepository urlRepository;
    private UrlDtoConverter urlDtoConverter;

    private UrlService urlService;

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
    void testGetUrlByShortUrl_itShouldReturnShortUrl() {
        Url url = generateUrl();

        Mockito.when(urlRepository.findByShortUrl(url.getShortUrl())).thenReturn(Optional.of(url));

        Url result = urlService.getShortUrl(url.getShortUrl());

        assertEquals(url, result);

        Mockito.verify(urlRepository).findByShortUrl(url.getShortUrl());
    }


    @Test
    void testGetUrl_itShouldReturnShortUrlToLongUrl() throws URISyntaxException {
        Url url = generateUrl();
        URI uri = new URI(url.getLongUrl());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        Mockito.when(urlRepository.findByShortUrl(url.getShortUrl())).thenReturn(Optional.of(url));

        HttpHeaders result = urlService.getUrl(url.getShortUrl());
        assertEquals(httpHeaders, result);
    }


    @Test
    void testGetUrlList_itShouldReturnListOfUrlDto(){

        List<Url> urlList = generateUrlList();
        List<UrlDto> urlDtoList = generateUrlDtoList();

        Mockito.when(urlRepository.findAll()).thenReturn(urlList);
        Mockito.when(urlDtoConverter.convertToUrlDtoList(urlList)).thenReturn(urlDtoList);

        List<UrlDto> result = urlService.getUrlDtoList();

        assertEquals(urlDtoList,result);

        Mockito.verify(urlRepository).findAll();
        Mockito.verify(urlDtoConverter).convertToUrlDtoList(urlList);
    }


    @Test
    void testCreateUrl_whenGetValidRequest_itShouldReturnUrlDto() {

        CreateShortUrlRequest createShortUrlRequest = generateCreateShortUrlRequest();
        Url expectedUrl = generateUrl();
      //  UrlDto expectedUrlDto = generateUrlDto();

        Mockito.when(urlRepository.save(expectedUrl)).thenReturn(expectedUrl);
       // Mockito.when(urlDtoConverter.convertToUrlDto(expectedUrl)).thenReturn(expectedUrlDto);

        String result = urlService.createShortUrl(createShortUrlRequest);

        assertEquals("short url -> " + createShortUrlRequest.getShortUrl() + expectedUrl.getId(),result);
        //assertEquals(urlService.getUrl(expectedUrl));

        Mockito.verify(urlRepository).save(expectedUrl);
        Mockito.verify(urlDtoConverter).convertToUrlDto(urlRepository.save(expectedUrl));
    }


    @Test
    void testDeleteUrl_whenExistId_itShouldReturnString() {

        Url expectedUrl = generateUrl();
        UrlDto expectedUrlDto = generateUrlDto();


        Mockito.when(urlRepository.findById(99)).thenReturn(Optional.of(expectedUrl));
        Mockito.when(urlDtoConverter.convertToUrlDto(expectedUrl)).thenReturn(expectedUrlDto);


        String result = urlService.deleteUrlById(99);

        assertEquals("url deleted successfully " + 99 , result);

        Mockito.verify(urlRepository).findById(99);
        Mockito.verify(urlDtoConverter).convertToUrlDto(expectedUrl);
    }

    @Test
    void testDeleteUrl_whenNotExistId_itShouldThrowUrlNotFoundException() {

        Mockito.when(urlRepository.findById(99)).thenThrow(UrlNotFoundException.class);

        assertThrows(UrlNotFoundException.class, () -> urlService.getUrlById(99));

        Mockito.verify(urlRepository).findById(99);
        Mockito.verifyNoInteractions(urlDtoConverter);
    }

}
