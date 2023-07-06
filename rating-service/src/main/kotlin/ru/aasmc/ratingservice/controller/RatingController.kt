package ru.aasmc.ratingservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.ratingservice.service.RatingService

private val log = LoggerFactory.getLogger(RatingController::class.java)

@RestController
@RequestMapping("/rating")
class RatingController(
        private val ratingService: RatingService
) {

    @GetMapping("/films/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmIdsForUser(@PathVariable("userId") userId: Long): List<Long> {
        log.info("Received request to GET all film ids liked by user with ID={}", userId)
        val filmIds = ratingService.getFilmsLikedByUser(userId)
        log.info("Film ids liked by user with id={}. {}", userId, filmIds)
        return filmIds
    }

    @GetMapping("/{filmId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmRating(@PathVariable("filmId") filmId: Long): Double {
        log.info("Received request to GET rating of film with ID={}", filmId)
        val rating = ratingService.getFilmRating(filmId)
        log.info("Rating of film with ID={}, {}", filmId, rating)
        return rating
    }

}