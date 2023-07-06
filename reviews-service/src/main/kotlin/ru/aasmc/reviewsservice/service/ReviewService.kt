package ru.aasmc.reviewsservice.service

import ru.aasmc.reviewsservice.dto.ReviewDto

interface ReviewService {
    fun addLike(reviewId: Long, userId: Long)

    fun addDislike(reviewId: Long, userId: Long)

    fun deleteLike(reviewId: Long, userId: Long)

    fun deleteDislike(reviewId: Long, userId: Long)

    fun create(dto: ReviewDto): ReviewDto

    fun update(dto: ReviewDto): ReviewDto

    fun delete(reviewId: Long)

    fun getReviewById(reviewId: Long): ReviewDto

    fun getAllReviews(filmId: Long, count: Int): List<ReviewDto>
}