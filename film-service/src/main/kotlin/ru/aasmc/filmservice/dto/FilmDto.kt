package ru.aasmc.filmservice.dto

import java.time.LocalDate

data class FilmDto(
    val filmId: Long,
    val name: String,
    val description: String,
    val releaseDate: LocalDate,
    val duration: Int,
    val mpa: MpaDto,
    val rate: Double,
    val genres: Set<GenreDto>,
    val directors: Set<DirectorDto>
)
