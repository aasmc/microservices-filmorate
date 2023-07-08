package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.FilmDto

interface RecommendationsService {

    fun getRecommendations(userId: Long): List<FilmDto>

}