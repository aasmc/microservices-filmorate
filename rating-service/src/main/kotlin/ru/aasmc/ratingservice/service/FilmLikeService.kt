package ru.aasmc.ratingservice.service

import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId

interface FilmLikeService {
    fun saveFilmLike(filmLike: FilmLike)

    fun removeFilmLike(id: FilmLikeId)

    fun removeAllFilmLikes(filmId: Long, timeStamp: Long)
}