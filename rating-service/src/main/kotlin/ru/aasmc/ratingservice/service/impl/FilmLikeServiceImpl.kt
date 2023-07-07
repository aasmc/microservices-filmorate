package ru.aasmc.ratingservice.service.impl

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.ratingservice.appevents.UpdateRatingEvent
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId
import ru.aasmc.ratingservice.repository.FilmLikeDataRepository
import ru.aasmc.ratingservice.service.FilmLikeService
import ru.aasmc.ratingservice.service.RatingService

@Service
@Transactional
class FilmLikeServiceImpl(
        private val filmLikeRepo: FilmLikeDataRepository,
        private val ratingService: RatingService,
        private val applicationEventPublisher: ApplicationEventPublisher
) : FilmLikeService {
    override fun saveFilmLike(filmLike: FilmLike) {
        filmLikeRepo.save(filmLike)
        publishNewRatingEvent(filmLike.id!!)
    }

    override fun removeFilmLike(id: FilmLikeId) {
        filmLikeRepo.removeFilmLikeById(id)
        publishNewRatingEvent(id)
    }

    override fun removeAllFilmLikes(filmId: Long, timeStamp: Long) {
        filmLikeRepo.removeAllById_FilmIdAndTimestampLessThanEqual(filmId, timeStamp)
    }

    private fun publishNewRatingEvent(id: FilmLikeId) {
        val newRating = ratingService.getFilmRating(id.filmId)
        applicationEventPublisher.publishEvent(
                UpdateRatingEvent(
                        source = this,
                        filmId = id.filmId,
                        newRate = newRating
                )
        )
    }
}