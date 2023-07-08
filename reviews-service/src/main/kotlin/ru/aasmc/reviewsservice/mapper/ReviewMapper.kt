package ru.aasmc.reviewsservice.mapper

import org.springframework.stereotype.Component
import ru.aasmc.reviewsservice.dto.ReviewDto
import ru.aasmc.reviewsservice.model.Review

@Component
class ReviewMapper: Mapper<Review, ReviewDto> {
    override fun mapToDomain(dto: ReviewDto): Review {
        return Review(
                content = dto.content,
                isPositive = dto.isPositive!!,
                userId = dto.userId!!,
                filmId = dto.filmId!!,
                useful = dto.useful ?: 0,
                id = dto.reviewId
        )
    }

    override fun mapToDto(domain: Review): ReviewDto {
        return ReviewDto(
                reviewId = domain.id,
                content = domain.content,
                isPositive = domain.isPositive,
                userId = domain.userId,
                filmId = domain.filmId,
                useful = domain.useful
        )
    }
}