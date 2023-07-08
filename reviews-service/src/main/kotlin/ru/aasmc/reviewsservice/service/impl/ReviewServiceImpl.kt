package ru.aasmc.reviewsservice.service.impl

import org.springframework.context.ApplicationEventPublisher
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.reviewsservice.appevents.EventOperation
import ru.aasmc.reviewsservice.appevents.ReviewEvent
import ru.aasmc.reviewsservice.client.FilmServiceClient
import ru.aasmc.reviewsservice.client.UserServiceClient
import ru.aasmc.reviewsservice.dto.ReviewDto
import ru.aasmc.reviewsservice.error.ReviewServiceException
import ru.aasmc.reviewsservice.mapper.ReviewMapper
import ru.aasmc.reviewsservice.repository.ReviewRepository
import ru.aasmc.reviewsservice.service.ReviewService
import java.lang.RuntimeException
import java.time.Instant

@Service
@Transactional
class ReviewServiceImpl(
        private val reviewRepository: ReviewRepository,
        private val mapper: ReviewMapper,
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val filmClient: FilmServiceClient,
        private val userClient: UserServiceClient
) : ReviewService {
    override fun addLike(reviewId: Long, userId: Long) {
        checkUserExists(userId)
        try {
            reviewRepository.addLike(reviewId, userId)
            reviewRepository.increaseUseful(reviewId)
        } catch (ex: RuntimeException) {
            if (ex is DataIntegrityViolationException) {
                val message = "Review with ID=$reviewId not found."
                throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), message)
            }
            throw ex
        }
    }

    override fun addDislike(reviewId: Long, userId: Long) {
        checkUserExists(userId)
        try {
            reviewRepository.addDislike(reviewId, userId)
            reviewRepository.decreaseUseful(reviewId)
        } catch (ex: RuntimeException) {
            if (ex is DataIntegrityViolationException) {
                val message = "Review with ID=$reviewId not found."
                throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), message)
            }
            throw ex
        }
    }

    override fun deleteLike(reviewId: Long, userId: Long) {
        checkUserExists(userId)
        try {
            reviewRepository.deleteLike(reviewId, userId)
            reviewRepository.decreaseUseful(reviewId)
        } catch (ex: RuntimeException) {
            if (ex is DataIntegrityViolationException) {
                val message = "Review with ID=$reviewId not found."
                throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), message)
            }
            throw ex
        }
    }

    override fun deleteDislike(reviewId: Long, userId: Long) {
        checkUserExists(userId)
        try {
            reviewRepository.deleteDislike(reviewId, userId)
            reviewRepository.increaseUseful(reviewId)
        } catch (ex: RuntimeException) {
            if (ex is DataIntegrityViolationException) {
                val message = "Review with ID=$reviewId not found."
                throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), message)
            }
            throw ex
        }
    }

    override fun create(dto: ReviewDto): ReviewDto {
        checkFilmUserExist(dto.filmId!!, dto.userId!!)
        val toSave = mapper.mapToDomain(dto)
        val saved = reviewRepository.save(toSave)
        applicationEventPublisher.publishEvent(
                ReviewEvent(
                        source = this,
                        timeStamp = Instant.now().toEpochMilli(),
                        reviewId = saved.id!!,
                        operation = EventOperation.ADD,
                        userId = dto.userId
                )
        )
        return mapper.mapToDto(saved)
    }

    override fun update(dto: ReviewDto): ReviewDto {
        checkFilmUserExist(dto.filmId!!, dto.userId!!)
        val rowsUpdated = reviewRepository.updateReview(dto.content, dto.isPositive!!, dto.reviewId ?: 0)
        if (rowsUpdated == 0) {
            val msg = "Review with ID=${dto.reviewId} not found."
            throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), msg)
        }
        applicationEventPublisher.publishEvent(
                ReviewEvent(
                        source = this,
                        timeStamp = Instant.now().toEpochMilli(),
                        reviewId = dto.reviewId!!,
                        operation = EventOperation.UPDATE,
                        userId = dto.userId
                )
        )
        return dto
    }

    override fun delete(reviewId: Long) {
        val review = reviewRepository.findById(reviewId)
                .orElse(null)
        if (review != null) {
            reviewRepository.deleteById(reviewId)
            applicationEventPublisher.publishEvent(
                    ReviewEvent(
                            source = this,
                            timeStamp = Instant.now().toEpochMilli(),
                            reviewId = reviewId,
                            operation = EventOperation.REMOVE,
                            userId = review.userId
                    )
            )
        }
    }

    override fun getReviewById(reviewId: Long): ReviewDto {
        val review = reviewRepository.findById(reviewId)
                .orElseThrow {
                    val msg = "Review with ID=$reviewId not found"
                    ReviewServiceException(HttpStatus.NOT_FOUND.value(), msg)
                }
        return mapper.mapToDto(review)
    }

    override fun getAllReviews(filmId: Long, count: Int): List<ReviewDto> {
        if (filmId == 0L) {
            return reviewRepository.findAllReviews(count)
                    .map { mapper.mapToDto(it) }
        }
        return reviewRepository.findAllReviewsForFilm(filmId, count)
                .map { mapper.mapToDto(it) }
    }

    private fun checkFilmUserExist(filmId: Long, userId: Long) {
        checkFilmExists(filmId)
        checkUserExists(userId)
    }

    private fun checkFilmExists(filmId: Long) {
        if (!filmClient.isFilmExists(filmId)) {
            val msg = "Film with ID=$filmId not found."
            throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), msg)
        }
    }

    private fun checkUserExists(userId: Long) {
        if (!userClient.isUserExists(userId)) {
            val msg = "User with ID=$userId not found."
            throw ReviewServiceException(HttpStatus.NOT_FOUND.value(), msg)
        }
    }
}