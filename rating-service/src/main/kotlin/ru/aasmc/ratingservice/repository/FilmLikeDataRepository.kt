package ru.aasmc.ratingservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId

interface FilmLikeDataRepository : JpaRepository<FilmLike, FilmLikeId> {

    fun removeFilmLikeById(id: FilmLikeId)

    fun removeAllById_FilmIdAndTimestampLessThanEqual(filmId: Long, timestamp: Long)

    @Query("""
        select avg(l.mark) as rate from likes l where l.film_id = ? 
    """, nativeQuery = true)
    fun findFilmRating(filmId: Long): Double

    @Query(
            """
                select ss.uId from
                (
                    select user_id as uId, avg(mark) as average from  
                    Likes where user_id != ?  
                    and film_id in 
                        (
                          select ll.film_id from Likes ll 
                          where ll.user_id = ? and ll.mark > 5.0
                        ) 
                    group by uId  
                    order by avg(mark) desc 
                    limit 1     
                ) as ss 
            """,
            nativeQuery = true
    )
    fun findRecommendedUserIdForUser(userId: Long, id: Long): Long

    @Query(
            """
                select film_id from likes 
                where user_id = ? and 
                film_id not in (
                    select ll.film_id from likes ll
                    where ll.user_id = ?
                )
            """,
            nativeQuery = true
    )
    fun findRecommendedFilmIds(recommendedUserId: Long, userId: Long): List<Long>

    @Query("""
                select film_id from likes 
                where user_id = ?
           """,
            nativeQuery = true)
    fun findFilmIdsLikedByUser(userId: Long): List<Long>
}