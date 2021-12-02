package com.md.urlshortener.dto.converter;

import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.model.Url;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlDtoConverter {

    public UrlDto convertToUrlDto(Url from) {
        return new UrlDto(
                from.getShortUrl(),
                from.getLongUrl(),
                from.getCreationDate()
        );
    }

    public List<UrlDto> convertToUrlDtoList(List<Url> urls) {
        return urls.stream().map(this::convertToUrlDto).collect(Collectors.toList());
    }
}
