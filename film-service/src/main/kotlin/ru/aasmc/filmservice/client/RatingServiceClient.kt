package ru.aasmc.filmservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "rating-service-client", url = "\${urls.ratingService}")
interface RatingServiceClient {

    @GetMapping("/rating/films/{userId}")
    fun getFilmIdsOfUser(@PathVariable("userId") userId: Long): List<Long>

    @GetMapping("/rating/{filmId}")
    fun getFilmRating(@PathVariable("filmId") filmId: Long): Double

}