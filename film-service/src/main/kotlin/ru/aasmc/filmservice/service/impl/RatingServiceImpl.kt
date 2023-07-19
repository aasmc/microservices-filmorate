package ru.aasmc.filmservice.service.impl

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import ru.aasmc.filmservice.client.RatingServiceClient
import ru.aasmc.filmservice.service.RatingService

@Service
class RatingServiceImpl(
        private val ratingServiceClient: RatingServiceClient
) : RatingService {

    @Retry(name = "retryRatingClient")
    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead", type = Bulkhead.Type.THREADPOOL)
    override fun getFilmIdsOfUser(userId: Long): List<Long> {
        return ratingServiceClient.getFilmIdsOfUser(userId)
    }

    @Retry(name = "retryRatingClient")
    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead", type = Bulkhead.Type.THREADPOOL)
    override fun getFilmRating(filmId: Long): Double {
        return ratingServiceClient.getFilmRating(filmId)
    }

    @Retry(name = "retryRatingClient")
    @CircuitBreaker(name = "ratingClient")
    @Bulkhead(name = "ratingClientBulkhead", type = Bulkhead.Type.THREADPOOL)
    override fun getRecommendedFilmIdsforUser(userId: Long): List<Long> {
        return ratingServiceClient.getRecommendedFilmIdsforUser(userId)
    }
}