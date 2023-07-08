package ru.aasmc.ratingservice.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId

interface FilmLikeDataRepository : MongoRepository<FilmLike, FilmLikeId> {

    fun removeFilmLikeById(id: FilmLikeId)

    fun removeAllById_FilmIdAndTimestampLessThanEqual(filmId: Long, timestamp: Long)

    @Query("{ '_id.userId': ?0 }")
    fun findAllFilmIdByUserId(userId: Long): List<FilmLike>

    @Query("{'_id.filmId': ?0 }")
    fun findAllMarksByFilmId(filmId: Long): List<FilmLike>

}