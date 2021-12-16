package com.md.urlshortener.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Url @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Integer?,

        var shortUrl: String,

        var longUrl: String,

        var creationDate: LocalDateTime = LocalDateTime.now()
) {

    fun copyUrl(url: Url): Url {
        val result = Url(id, shortUrl, longUrl, creationDate)

        result.id = url.id
        result.shortUrl = url.shortUrl
        result.longUrl = url.longUrl
        result.creationDate = url.creationDate

        return result
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Url

        if (shortUrl != other.shortUrl) return false
        if (longUrl != other.longUrl) return false
//        if (creationDate != other.creationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shortUrl.hashCode()
        result = 31 * result + longUrl.hashCode()
//        result = 31 * result + creationDate.hashCode()
        return result
    }

}
