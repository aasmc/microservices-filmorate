package ru.aasmc.userservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.aasmc.userservice.dto.FilmDto

@FeignClient("film-service")
interface RecommendationsClient {

    @GetMapping("/recommendations/{id}")
    fun getRecommendations(@PathVariable("id") userId: Long): List<FilmDto>

}