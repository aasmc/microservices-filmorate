package ru.aasmc.filmservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.exceptions.ErrorResponse
import ru.aasmc.filmservice.service.RecommendationsService

private val log = LoggerFactory.getLogger(RecommendationsController::class.java)

@RestController
@RequestMapping("/recommendations")
class RecommendationsController(
        private val recommendationsService: RecommendationsService
) {

    @Operation(summary = "Retrieves a list of films recommended to the specified user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "User with specified ID not found.",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}")
    fun getRecommendedFilms(@PathVariable("id") userId: Long): List<FilmDto> {
        log.info("Received request to GET film recommendations for user with ID={}", userId)
        return recommendationsService.getRecommendations(userId)
    }

}