package ru.aasmc.filmservice.model.mapper

import org.springframework.stereotype.Component
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.model.Film
import ru.aasmc.filmservice.model.mapToDto
import ru.aasmc.filmservice.storage.DirectorRepository
import ru.aasmc.filmservice.storage.GenreRepository
import ru.aasmc.filmservice.storage.MpaRepository
import kotlin.random.Random

@Component
class FilmMapper(
        private val genreRepository: GenreRepository,
        private val mpaRepository: MpaRepository,
        private val directorRepository: DirectorRepository
) : Mapper<Film, FilmRequest, FilmDto> {
    override fun mapToDomain(dto: FilmRequest): Film {
        val mpa = mpaRepository.findById(dto.mpa.id)
                .orElseThrow {
                    ResourceNotFoundException(message = "Mpa with ID=${dto.mpa} not found in DB.")
                }
        val genres = genreRepository.findAllByIdIn(dto.genres.map { it.id })
        val directors = directorRepository.findAllByIdIn(dto.directors.map { it.id })
        return Film(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                releaseDate = dto.releaseDate,
                duration = dto.duration,
                mpa = mpa,
                genres = genres,
                directors = directors,
                rate = calculateRate(dto.id)
        )
    }

    override fun mapToDto(domain: Film): FilmDto {
        return FilmDto(
                filmId = domain.id ?: throw IllegalStateException("Film from DB doesn't contain ID!"),
                name = domain.name,
                description = domain.description,
                releaseDate = domain.releaseDate,
                duration = domain.duration,
                mpa = domain.mpa.mapToDto(),
                genres = domain.genres.map { it.mapToDto() }.toSet(),
                directors = domain.directors.map { it.mapToDto() }.toSet(),
                rate = domain.rate
        )
    }

    private fun calculateRate(filmId: Long?): Double {
        if (filmId == null) return 0.0
        return Random.nextDouble(10.0) // TODO
    }
}