package ru.aasmc.reviewsservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.aasmc.reviewsservice.model.Review

interface ReviewRepository : JpaRepository<Review, Long> {

    @Modifying
    @Query("update reviews set content = ?, is_positive = ? where id = ?", nativeQuery = true)
    fun updateReview(content: String, isPositive: Boolean, reviewId: Long): Int

    @Modifying
    @Query("update Reviews set useful = useful + 1 where id = ?", nativeQuery = true)
    fun increaseUseful(reviewId: Long)

    @Modifying
    @Query("update Reviews set useful = useful - 1 where id = ?", nativeQuery = true)
    fun decreaseUseful(reviewId: Long)

    @Query("select * from Reviews where film_id = ? order by useful desc, id limit ?", nativeQuery = true)
    fun findAllReviewsForFilm(filmId: Long, count: Int): List<Review>

    @Query("select * from Reviews order by useful desc, id limit ?", nativeQuery = true)
    fun findAllReviews(count: Int): List<Review>

    @Modifying
    @Query("""
        insert into reviews_likes (review_id, user_id, is_like) 
        values(?, ?, true)
    """, nativeQuery = true)
    fun addLike(reviewId: Long, userId: Long)

    @Modifying
    @Query("""
        insert into reviews_likes (review_id, user_id, is_like) 
        values(?, ?, false)
    """, nativeQuery = true)
    fun addDislike(reviewId: Long, userId: Long)

    @Modifying
    @Query("""
        delete from reviews_likes where review_id = ? and user_id = ? and is_like = true
    """, nativeQuery = true)
    fun deleteLike(reviewId: Long, userId: Long)

    @Modifying
    @Query("""
        delete from reviews_likes where review_id = ? and user_id = ? and is_like = false
    """, nativeQuery = true)
    fun deleteDislike(reviewId: Long, userId: Long)

}