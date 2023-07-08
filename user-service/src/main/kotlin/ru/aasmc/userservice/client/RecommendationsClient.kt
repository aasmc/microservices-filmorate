package ru.aasmc.userservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.aasmc.userservice.dto.FilmDto

@FeignClient(name = "recommendations-service-client", url = "\${urls.recommendationsClient}")
interface RecommendationsClient {

    @GetMapping("/recommendations/{id}")
    fun getRecommendations(@PathVariable("id") userId: Long): List<FilmDto>

}