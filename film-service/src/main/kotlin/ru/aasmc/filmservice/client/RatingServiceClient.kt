package ru.aasmc.filmservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "rating-service-client", url = "\${urls.ratingService}")
interface RatingServiceClient {

    @GetMapping("/films/{userId}")
    fun getFilmIdsOfUser(@PathVariable("userId") userId: Long): List<Long>

}