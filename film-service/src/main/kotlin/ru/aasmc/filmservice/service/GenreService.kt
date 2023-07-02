package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.GenreDto

interface GenreService {
    fun getAll(): List<GenreDto>

    fun getById(id: Int): GenreDto
}