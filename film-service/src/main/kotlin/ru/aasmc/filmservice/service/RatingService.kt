package ru.aasmc.filmservice.service

interface RatingService {
    fun getFilmIdsOfUser(userId: Long): List<Long>
    fun getFilmRating(filmId: Long): Double
    fun getRecommendedFilmIdsforUser( userId: Long): List<Long>
}