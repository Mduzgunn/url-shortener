package com.md.urlshortener.dto.request

import javax.validation.constraints.NotBlank

data class CreateShortUrlRequest(
        val shortUrl: String,
        val longUrl: String
)