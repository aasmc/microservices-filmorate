package ru.aasmc.userservice.dto

import java.time.LocalDate

data class FilmDto(
        val id: Long,
        val name: String,
        val description: String,
        val releaseDate: LocalDate,
        val duration: Int,
        val mpa: MpaDto?,
        val rate: Double,
        val genres: List<GenreDto>?,
        val directors: List<DirectorDto>?
)
