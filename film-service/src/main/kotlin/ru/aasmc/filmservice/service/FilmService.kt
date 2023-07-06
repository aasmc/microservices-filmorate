package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.model.SearchBy
import ru.aasmc.filmservice.model.SortBy

interface FilmService {

    fun getById(filmId: Long): FilmDto
    fun create(request: FilmRequest): FilmDto
    fun delete(id: Long)
    fun getAll(): List<FilmDto>
    fun update(request: FilmRequest): FilmDto
    fun getTopFilms(count: Int, genreId: Int? = null, year: Int? = null): List<FilmDto>
    fun getFilmsOfDirector(id: Long, sort: SortBy): List<FilmDto>
    fun search(query: String, by: List<SearchBy>): List<FilmDto>
    fun userExists(id: Long): Boolean
    fun isFilmExists(filmId: Long): Boolean
}