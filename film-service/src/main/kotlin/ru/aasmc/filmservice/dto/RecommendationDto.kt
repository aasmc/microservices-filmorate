package ru.aasmc.filmservice.dto

data class RecommendationDto(
        val recommendedUserId: Long,
        val filmIdsLikedByUser: List<Long>
)
