package ru.aasmc.filmservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.aasmc.filmservice.dto.DirectorDto
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.dto.FilmRequest
import ru.aasmc.filmservice.exceptions.ErrorResponse
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

    @Operation(summary = "Adds like to film with specified ID by user with specified ID, if film and user are known to server.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Either film or user not found",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                )]
        )
    ])
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

    @Operation(summary = "Removes like from film with specified ID by user with specified ID, if film and user are known to server.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success"
        ),
        ApiResponse(
                responseCode = "404",
                description = "Either film or user not found",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                )]
        )
    ])
    @DeleteMapping("/{id}/like/{userId}")
    fun deleteLike(@PathVariable("id") filmId: Long, @PathVariable("userId") userId: Long) {
        log.info(
                "Received request to DELETE like from film with ID={} by user with ID={}",
                filmId, userId
        )
        filmLikeService.removeLike(filmId, userId)
    }

    @Operation(summary = "Retrieves a film by its ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = FilmDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Film is not found",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                )]
        )
    ])
    @GetMapping("/{id}")
    fun getFilmById(@PathVariable("id") filmId: Long): FilmDto {
        log.info("Received request to GET film by id={}", filmId)
        return filmService.getById(filmId)
    }

    @Operation(summary = "Retrieves a list of popular films. Number of films is determined by " +
            "'count' parameter. User can optionally specify a film genre - param 'genreId' and a film year " +
            "- param 'year'.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        )
    ])
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

    @Operation(summary = "Retrieves a list of all films stored on the server.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<FilmDto> {
        log.info("Received request to GET all films.")
        return filmService.getAll()
    }

    @Operation(summary = "Creates a film if the request is valid.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "201",
                description = "Successfully created film.",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = FilmDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "404",
                description = "Mpa not found.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: FilmRequest): FilmDto {
        log.info("Received POST request to create film. RequestBody={}", request)
        return filmService.create(request)
    }

    @Operation(summary = "Updates a film if the request is valid.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully updated the film.",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = FilmDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "404",
                description = "Either Film with ID is not found, or some of the film relations (Mpa, Genre, Director) is not found.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@Valid @RequestBody request: FilmRequest): FilmDto {
        log.info("Received PUT request to update film. RequestBody={}", request)
        return filmService.update(request)
    }

    @Operation(summary = "Returns a list of films of a given director. Sorted either by rating (likes), or by year.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Director not found.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Searches for films. Search params can be: title, director. " +
            "If a film's title or a director's name contains the given query, then " +
            "the film is returned to the client.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        )
    ])
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    fun search(
            @RequestParam("query") query: String,
            @RequestParam("by") searchBy: List<SearchBy>
    ): List<FilmDto> {
        log.info(
                "Received GET request to search for films by query={}, searchFilters={}",
                query,
                searchBy
        )
        return filmService.search(query, searchBy)
    }

    @Operation(summary = "Deletes a film by its ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Success"
        )
    ])
    @DeleteMapping("/{filmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFilm(@PathVariable("filmId") filmId: Long) {
        log.info("Received request to DELETE film with id={}", filmId)
        filmService.delete(filmId)
    }

    @Operation(summary = "Checks if the film with specified ID exists on the server.")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = Boolean::class))]
    )
    @GetMapping("/exists/{filmId}")
    @ResponseStatus(HttpStatus.OK)
    fun isFilmExists(@PathVariable("filmId") filmId: Long): Boolean {
        log.info("Received GET request to check if Film with ID={} exists.", filmId)
        return filmService.isFilmExists(filmId)
    }

    @Operation(summary = "Returns a list of films liked by specified users.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = FilmDto::class))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Either user or friend not found.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/common")
    @ResponseStatus(HttpStatus.OK)
    fun getCommonFilms(
            @RequestParam("userId") userId: Long,
            @RequestParam("friendId") friendId: Long
    ): List<FilmDto> {
        log.info(
                "Received request to GET common films for user with ID={} and friend with ID={}",
                userId,
                friendId
        )
        return filmService.getCommonFilms(userId, friendId)
    }
}
