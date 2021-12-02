package com.md.urlshortener.dto

import java.time.LocalDateTime

data class UrlDto @JvmOverloads constructor(
        val shortUrl: String,
        val longUrl: String,
        val creationDate: LocalDateTime?

)
