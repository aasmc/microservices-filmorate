package ru.aasmc.ratingservice.service.impl

import org.springframework.stereotype.Service
import ru.aasmc.ratingservice.repository.FilmLikeDataRepository
import ru.aasmc.ratingservice.service.RatingService

@Service
class RatingServiceImpl(
        private val filmLikeRepo: FilmLikeDataRepository
) : RatingService {
    override fun getFilmsLikedByUser(userId: Long): List<Long> {
        return filmLikeRepo.findAllFilmIdByUserId(userId)
    }

    override fun getFilmRating(filmId: Long): Double {
        val marks = filmLikeRepo.findAllMarksByFilmId(filmId)
        return if (marks.isEmpty()) {
            0.0
        } else {
            marks.average()
        }
    }
}