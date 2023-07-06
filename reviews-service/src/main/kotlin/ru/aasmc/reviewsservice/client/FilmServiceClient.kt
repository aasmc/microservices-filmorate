package ru.aasmc.reviewsservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service-client", url = "\${urls.filmService}")
interface FilmServiceClient {

    @GetMapping("/films/exists/{filmId}")
    fun isFilmExists(@PathVariable("filmId") filmId: Long): Boolean

}