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
import ru.aasmc.filmservice.service.RatingService
import ru.aasmc.filmservice.storage.DirectorRepository
import ru.aasmc.filmservice.storage.FilmRepository
import ru.aasmc.filmservice.storage.FilmSpecificationBuilder
import java.time.Instant

@Service
class FilmServiceImpl(
        private val filmRepo: FilmRepository,
        private val mapper: FilmMapper,
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val directorRepo: DirectorRepository,
        private val ratingService: RatingService
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
        if (filmRepo.existsById(request.id ?: 0)) {
            val toUpdate = mapper.mapToDomain(request)
            val updated = filmRepo.save(toUpdate)
            return mapper.mapToDto(updated)
        }
        throw ResourceNotFoundException(message = "Film with ID=${request.id} not found.")
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
            SortBy.year -> dtos.sortedBy { it.releaseDate }
            SortBy.likes -> dtos.sortedWith(compareByDescending<FilmDto> { it.rate }.thenBy { it.id })
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

    override fun userExists(id: Long): Boolean {
        return filmRepo.getReferenceById(id).id != null
    }

    override fun isFilmExists(filmId: Long): Boolean {
        return filmRepo.existsById(filmId)
    }

    override fun getCommonFilms(userId: Long, friendId: Long): List<FilmDto> {
        val userFilmIds = getFilmIdsForUser(userId)
        val friendFilmIds = getFilmIdsForUser(friendId)
        val userFilms = filmRepo.findAllByIdInOrderByRateDesc(userFilmIds)
                .toHashSet()
        val friendFilms = filmRepo.findAllByIdInOrderByRateDesc(friendFilmIds)
                .toHashSet()
        userFilms.retainAll(friendFilms)
        return userFilms.map { mapper.mapToDto(it) }
                .sortedByDescending { it.rate }
    }
    private fun getFilmIdsForUser(userId: Long): List<Long> {
        return ratingService.getFilmIdsOfUser(userId)
    }

    @Transactional
    override fun setRateToFilm(filmId: Long, rate: Double) {
        filmRepo.setRateToFilm(rate, filmId)
    }
}