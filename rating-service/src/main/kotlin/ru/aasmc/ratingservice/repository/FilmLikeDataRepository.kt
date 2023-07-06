package ru.aasmc.ratingservice.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId

interface FilmLikeDataRepository : MongoRepository<FilmLike, FilmLikeId> {

    fun removeFilmLikeByIdAndTimestampEquals(id: FilmLikeId, timestamp: Long)

    fun removeAllById_FilmIdAndTimestampLessThanEqual(filmId: Long, timestamp: Long)

    @Query("{ '_id.userId': ?0 }", fields = "{'_id': 0, '_id.filmId': 1}")
    fun findAllFilmIdByUserId(userId: Long): List<Long>

    @Query("{'_id.filmId': ?0 }", fields = "{'_id': 0, 'mark': 1}")
    fun findAllMarksByFilmId(filmId: Long): List<Int>

}