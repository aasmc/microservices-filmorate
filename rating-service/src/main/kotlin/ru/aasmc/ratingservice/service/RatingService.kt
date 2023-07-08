package ru.aasmc.ratingservice.service

interface RatingService {

    fun getFilmsLikedByUser(userId: Long): List<Long>

    fun getFilmRating(filmId: Long): Double

    fun getRecommendationForUser(userId: Long): List<Long>

}