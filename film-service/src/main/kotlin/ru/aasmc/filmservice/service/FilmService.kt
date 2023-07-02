package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.model.SearchBy

interface FilmService {
    fun create(request: FilmRequest): FilmDto
    fun delete(id: Long)
    fun getAll(): List<FilmDto>
    fun update(request: FilmRequest): FilmDto
    fun getTopFilms(count: Int, genreId: Int, year: Int)
    fun getFilmsOfDirector(id: Long): List<FilmDto>

    fun search(query: String, by: List<SearchBy>): List<FilmDto>
}