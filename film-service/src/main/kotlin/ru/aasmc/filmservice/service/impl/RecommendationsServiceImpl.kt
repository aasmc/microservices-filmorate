package ru.aasmc.filmservice.service.impl

import org.springframework.stereotype.Service
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.model.mapper.FilmMapper
import ru.aasmc.filmservice.service.RatingService
import ru.aasmc.filmservice.service.RecommendationsService
import ru.aasmc.filmservice.storage.FilmRepository

@Service
class RecommendationsServiceImpl(
        private val ratingService: RatingService,
        private val filmRepository: FilmRepository,
        private val mapper: FilmMapper
) : RecommendationsService {
    override fun getRecommendations(userId: Long): List<FilmDto> {
        val recommendation = ratingService.getRecommendedFilmIdsforUser(userId)
        return filmRepository.findAllByIdInOrderByRateDesc(recommendation)
                .map { mapper.mapToDto(it) }
    }
}