package ru.aasmc.ratingservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.aasmc.ratingservice.service.RatingService

private val log = LoggerFactory.getLogger(RatingController::class.java)

@RestController
@RequestMapping("/rating")
class RatingController(
        private val ratingService: RatingService
) {

    @Operation(summary = "Retrieves a list of IDs of Films liked by a user with specified ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = Long::class))
                )]
        )
    ])
    @GetMapping("/films/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmIdsForUser(@PathVariable("userId") userId: Long): List<Long> {
        log.info("Received request to GET all film ids liked by user with ID={}", userId)
        val filmIds = ratingService.getFilmsLikedByUser(userId)
        log.info("Film ids liked by user with id={}. {}", userId, filmIds)
        return filmIds
    }

    @Operation(summary = "Retrieves a rating of the film or 0.0 if DB contains no rating info.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = Double::class)
                )]
        )
    ])
    @GetMapping("/{filmId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmRating(@PathVariable("filmId") filmId: Long): Double {
        log.info("Received request to GET rating of film with ID={}", filmId)
        val rating = ratingService.getFilmRating(filmId)
        log.info("Rating of film with ID={}, {}", filmId, rating)
        return rating
    }

    @Operation(summary = "Retrieves a list of IDs of Films recommended to a user with specified ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = Long::class))
                )]
        )
    ])
    @GetMapping("/user/{userId}")
    fun getRecommendedUserForUser(@PathVariable("userId") userId: Long): List<Long> {
        log.info("Received request to GET id of user recommended for user with ID={}", userId)
        val recommendation = ratingService.getRecommendationForUser(userId)
        log.info("Recommendation for user with id={} is {}", userId, recommendation)
        return recommendation
    }
}