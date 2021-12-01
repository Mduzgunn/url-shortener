package com.md.urlshortener.dto.converter;

import com.md.urlshortener.dto.UrlDto;
import com.md.urlshortener.model.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlDtoConverter {

    public UrlDto convertToUrlDto(Url from){
        return new UrlDto(
                from.getShortUrl(),
                from.getLongUrl()
               // from.getCreationDate()
        );
    }

}
