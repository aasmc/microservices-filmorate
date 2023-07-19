package ru.aasmc.filmservice.service.impl

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import ru.aasmc.filmservice.client.RatingServiceClient
import ru.aasmc.filmservice.service.RatingService

@Service
class RatingServiceImpl(
        private val ratingServiceClient: RatingServiceClient
) : RatingService {

    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead")
    override fun getFilmIdsOfUser(userId: Long): List<Long> {
        return ratingServiceClient.getFilmIdsOfUser(userId)
    }

    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead")
    override fun getFilmRating(filmId: Long): Double {
        return ratingServiceClient.getFilmRating(filmId)
    }

    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead")
    override fun getRecommendedFilmIdsforUser(userId: Long): List<Long> {
        return ratingServiceClient.getRecommendedFilmIdsforUser(userId)
    }
}