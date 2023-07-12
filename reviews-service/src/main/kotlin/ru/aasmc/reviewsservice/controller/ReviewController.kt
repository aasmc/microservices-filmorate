package ru.aasmc.reviewsservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.aasmc.reviewsservice.dto.ReviewDto
import ru.aasmc.reviewsservice.error.ErrorResponse
import ru.aasmc.reviewsservice.service.ReviewService
import javax.validation.Valid

private val log = LoggerFactory.getLogger(ReviewController::class.java)

@RestController
@RequestMapping("/reviews")
class ReviewController(
        private val reviewService: ReviewService
) {

    @Operation(summary = "Updating film review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully updated film review",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ReviewDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find review / film / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PutMapping
    fun updateReview(@Valid @RequestBody dto: ReviewDto): ReviewDto {
        log.info("Received PUT request to update review {}", dto)
        return reviewService.update(dto)
    }

    @Operation(summary = "Creating film review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully created film review",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ReviewDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(@RequestBody @Valid dto: ReviewDto): ReviewDto {
        log.info("Received POST request to create ")
        return reviewService.create(dto)
    }

    @Operation(summary = "Get film review by ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ReviewDto::class))
                ]),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film review by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getReviewById(@PathVariable("id") id: Long): ReviewDto {
        log.info("Received request to GET review with ID={}", id)
        return reviewService.getReviewById(id)
    }

    @Operation(summary = "Get a list of all film reviews. If filmId != 0 returns a list of reviews of " +
            "the specified film, otherwise returns a list of all film reviews.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = ReviewDto::class)))
                )]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getReviews(
            @RequestParam("filmId", defaultValue = "0") filmId: Long,
            @RequestParam("count", defaultValue = "10") count: Int
    ): List<ReviewDto> {
        log.info("Received request to GET {} reviews of film with ID={}", count, filmId)
        return reviewService.getAllReviews(filmId, count)
    }

    @Operation(summary = "Deletes a film review with specified ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Success"
        )
    ])
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(@PathVariable("id") id: Long) {
        log.info("Received request to DELETE review with ID={}", id)
        reviewService.delete(id)
    }

    @Operation(summary = "Deletes a user's dislike from specified review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film review / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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


    @Operation(summary = "Deletes a user's like from specified review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film review / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Adds a user's dislike to the specified review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film review / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Adds a user's like to the specified review.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find film review / user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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