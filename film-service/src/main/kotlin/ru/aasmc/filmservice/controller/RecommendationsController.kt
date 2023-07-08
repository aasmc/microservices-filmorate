package ru.aasmc.filmservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.service.RecommendationsService

private val log = LoggerFactory.getLogger(RecommendationsController::class.java)

@RestController
@RequestMapping("/recommendations")
class RecommendationsController(
        private val recommendationsService: RecommendationsService
) {

    @GetMapping("/{id}")
    fun getRecommendedFilms(@PathVariable("id") userId: Long): List<FilmDto> {
        log.info("Received request to GET film recommendations for user with ID={}", userId)
        return recommendationsService.getRecommendations(userId)
    }

}