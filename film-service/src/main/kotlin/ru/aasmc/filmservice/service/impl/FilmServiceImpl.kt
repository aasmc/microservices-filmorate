package ru.aasmc.filmservice.service.impl

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.filmservice.appevents.AllLikesDeletedEvent
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.model.SearchBy
import ru.aasmc.filmservice.model.SortBy
import ru.aasmc.filmservice.model.mapper.FilmMapper
import ru.aasmc.filmservice.service.FilmService
import ru.aasmc.filmservice.storage.*
import java.time.Instant

@Service
class FilmServiceImpl(
        private val filmRepo: FilmRepository,
        private val mapper: FilmMapper,
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val genreRepo: GenreRepository,
        private val directorRepo: DirectorRepository
) : FilmService {

    override fun getById(filmId: Long): FilmDto {
        val film = filmRepo.findById(filmId)
                .orElseThrow {
                    ResourceNotFoundException(message = "Film with ID=$filmId not found in DB.")
                }
        return mapper.mapToDto(film)
    }

    @Transactional
    override fun create(request: FilmRequest): FilmDto {
        val film = mapper.mapToDomain(request)
        val saved = filmRepo.save(film)
        return mapper.mapToDto(saved)
    }

    @Transactional
    override fun delete(id: Long) {
        val deleted = filmRepo.deleteFilmById(id)
        if (deleted != 0) {
            applicationEventPublisher.publishEvent(
                    AllLikesDeletedEvent(
                            source = this,
                            filmId = id,
                            timeOfEvent = Instant.now().toEpochMilli()
                    )
            )
        }
    }

    override fun getAll(): List<FilmDto> {
        return filmRepo.findAllSorted()
                .map { mapper.mapToDto(it) }
    }

    @Transactional
    override fun update(request: FilmRequest): FilmDto {
        val toUpdate = mapper.mapToDomain(request)
        val updated = filmRepo.save(toUpdate)
        return mapper.mapToDto(updated)
    }

    override fun getTopFilms(count: Int, genreId: Int?, year: Int?): List<FilmDto> {
        if (genreId == null && year == null) {
            return filmRepo.findAllSortedLimit(PageRequest.of(0, count))
                    .map { mapper.mapToDto(it) }
        } else if (year == null && genreId != null) {
            return filmRepo.findAllByGenreSortedByRate(genreId, count)
                    .map { mapper.mapToDto(it) }
        } else if (genreId == null && year != null) {
            return filmRepo.findAllByYearOrderByRateDesc(year, PageRequest.of(0, count))
                    .map { mapper.mapToDto(it) }
        } else {
            return filmRepo.findAllByGenresAndYearSortedByRate(
                    genreId!!,
                    year!!,
                    count
            )
                    .map { mapper.mapToDto(it) }
        }
    }

    override fun getFilmsOfDirector(id: Long, sort: SortBy): List<FilmDto> {
        val films = directorRepo.findById(id)
                .orElseThrow {
                    ResourceNotFoundException(message = "Director with ID=$id not found in DB.")
                }
                .films
        val dtos = films.map { mapper.mapToDto(it) }
        return when (sort) {
            SortBy.year -> dtos.sortedByDescending { it.releaseDate }
            SortBy.likes -> dtos.sortedByDescending { it.rate }
        }
    }

    override fun search(query: String, by: List<SearchBy>): List<FilmDto> {
        val specBuilder = FilmSpecificationBuilder()
        by.forEach { b -> specBuilder.with(b) }
        val sort = Sort.by("rate")
                .descending()

        val films = filmRepo.findAll(specBuilder.build(query.lowercase()), sort)
        return films.map { mapper.mapToDto(it) }
    }
}