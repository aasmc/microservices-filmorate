package ru.aasmc.filmservice.service

interface FilmLikeService {
    fun addLike(filmId: Long, userId: Long, mark: Int)

    fun removeLike(filmId: Long, userId: Long)

    fun deleteAllLikes(filmId: Long, timestamp: Long)
}