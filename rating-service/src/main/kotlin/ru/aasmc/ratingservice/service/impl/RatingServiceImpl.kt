package ru.aasmc.ratingservice.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.InvalidDataAccessResourceUsageException
import org.springframework.stereotype.Service
import ru.aasmc.ratingservice.repository.FilmLikeDataRepository
import ru.aasmc.ratingservice.service.RatingService

@Service
class RatingServiceImpl(
        private val filmLikeRepo: FilmLikeDataRepository
) : RatingService {
    override fun getFilmsLikedByUser(userId: Long): List<Long> {
        return safeDbCall(userId, emptyList()) {
            filmLikeRepo.findFilmIdsLikedByUser(it)
        }
    }

    override fun getFilmRating(filmId: Long): Double {
        return safeDbCall(filmId, 0.0) {
            filmLikeRepo.findFilmRating(it)
        }
    }

    override fun getRecommendationForUser(userId: Long): List<Long> {

        val recommendedUserId = safeDbCall(userId, 0) {
            filmLikeRepo.findRecommendedUserIdForUser(it, it)
        }
        return filmLikeRepo.findRecommendedFilmIds(recommendedUserId, userId)
    }

    private fun <T, R> safeDbCall(t: T, default: R, block: (t: T) -> R): R {
        return try {
            block(t)
        } catch (e: RuntimeException) {
            if (e is EmptyResultDataAccessException) {
                default
            } else {
                throw e
            }
        }
    }

}