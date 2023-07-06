package ru.aasmc.reviewsservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.aasmc.reviewsservice.dto.ReviewDto
import ru.aasmc.reviewsservice.service.ReviewService
import javax.validation.Valid

private val log = LoggerFactory.getLogger(ReviewController::class.java)

@RestController
@RequestMapping("/reviews")
class ReviewController(
        private val reviewService: ReviewService
) {

    @PutMapping
    fun updateReview(@Valid @RequestBody dto: ReviewDto): ReviewDto {
        log.info("Received PUT request to update review {}", dto)
        return reviewService.update(dto)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(@RequestBody @Valid dto: ReviewDto): ReviewDto {
        log.info("Received POST request to create ")
        return reviewService.create(dto)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getReviewById(@PathVariable("id") id: Long): ReviewDto {
        log.info("Received request to GET review with ID={}", id)
        return reviewService.getReviewById(id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getReviews(
            @RequestParam("filmId", defaultValue = "0") filmId: Long,
            @RequestParam("count", defaultValue = "10") count: Int
    ): List<ReviewDto> {
        log.info("Received request to GET {} reviews of film with ID={}", count, filmId)
        return reviewService.getAllReviews(filmId, count)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(@PathVariable("id") id: Long) {
        log.info("Received request to DELETE review with ID={}", id)
        reviewService.delete(id)
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDislike(
            @PathVariable("id") id: Long,
            @PathVariable("userId") userId: Long
    ) {
        log.info(
                "Received request to DELETE dislike from review with ID={} by user with ID={}",
                id,
                userId
        )
        reviewService.deleteDislike(id, userId)
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLike(
            @PathVariable("id") id: Long,
            @PathVariable("userId") userId: Long
    ) {
        log.info(
                "Received request to DELETE like from review with ID={} by user with ID={}",
                id,
                userId
        )
        reviewService.deleteLike(id, userId)
    }

    @PutMapping("/{id}/dislike/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun addDislike(
            @PathVariable("id") id: Long,
            @PathVariable("userId") userId: Long
    ) {
        log.info(
                "Received request to PUT dislike to review with ID={} by user with ID={}",
                id,
                userId
        )
        reviewService.addDislike(id, userId)
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun addLike(@PathVariable("id") id: Long,
                @PathVariable("userId") userId: Long
    ) {
        log.info(
                "Received request to PUT like to review with ID={} by user with ID={}",
                id,
                userId
        )
        reviewService.addLike(id, userId)
    }

}