package ru.aasmc.filmservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.model.SearchBy
import ru.aasmc.filmservice.model.SortBy
import ru.aasmc.filmservice.service.FilmLikeService
import ru.aasmc.filmservice.service.FilmService
import javax.validation.Valid
import javax.validation.constraints.Positive

private val log = LoggerFactory.getLogger(FilmController::class.java)

@RestController
@RequestMapping("/films")
class FilmController(
    private val filmService: FilmService,
    private val filmLikeService: FilmLikeService
) {
    @PutMapping("/{id}/like/{userId}")
    fun addLike(
        @PathVariable("id") filmId: Long,
        @PathVariable("userId") userId: Long,
        @RequestParam(value = "mark", required = false, defaultValue = "10") mark: Int
    ) {
        log.info(
            "Received PUT request to add like with mark={} to film with ID={}, by user with ID={}",
            mark, filmId, userId
        )
        filmLikeService.addLike(filmId, userId, mark)
    }

    @DeleteMapping("/{id}/like/{userId}")
    fun deleteLike(@PathVariable("id") filmId: Long, @PathVariable("userId") userId: Long) {
        log.info(
            "Received request to DELETE like from film with ID={} by user with ID={}",
            filmId, userId
        )
        filmLikeService.removeLike(filmId, userId)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmById(@PathVariable("id") filmId: Long): FilmDto {
        log.info("Received request to GET film by id={}", filmId)
        return filmService.getById(filmId)
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    fun getPopular(
        @RequestParam(value = "count", defaultValue = "10", required = false)
        @Positive(message = "Значение count не может быть меньше 0.") count: Int,
        @RequestParam(value = "genreId", required = false)
        @Positive(message = "Значение genreId не может быть меньше 0.") genreId: Int?,
        @RequestParam(value = "year", required = false)
        @Positive(message = "Значение year не может быть меньше 0.") year: Int?
    ): List<FilmDto> {
        log.info(
            "Received request to GET popular films. Count={}, genreId={}, year={}",
            count, genreId, year
        )
        return filmService.getTopFilms(count, genreId, year)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<FilmDto> {
        log.info("Received request to GET all films.")
        return filmService.getAll()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: FilmRequest): FilmDto {
        log.info("Received POST request to create film. RequestBody={}", request)
        return filmService.create(request)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@Valid @RequestBody request: FilmRequest): FilmDto {
        log.info("Received PUT request to update film. RequestBody={}", request)
        return filmService.update(request)
    }

    @GetMapping("/director/{directorId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFilmsByDirector(
        @PathVariable("directorId") directorId: Long,
        @RequestParam(value = "sortBy", defaultValue = "year") sortBy: SortBy
    ): List<FilmDto> {
        log.info(
            "Received request to GET films of director with ID={}, sorted by={}",
            directorId, sortBy
        )
        return filmService.getFilmsOfDirector(directorId, sortBy)
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    fun search(
        @RequestParam query: String,
        @RequestParam searchBy: List<SearchBy>
    ): List<FilmDto> {
        log.info(
            "Received GET request to search for films by query={}, searchFilters={}",
            query,
            searchBy
        )
        return filmService.search(query, searchBy)
    }

    @DeleteMapping("/{filmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFilm(@PathVariable("filmId") filmId: Long) {
        log.info("Received request to DELETE film with id={}", filmId)
        filmService.delete(filmId)
    }
}
