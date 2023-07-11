package ru.aasmc.filmservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.FilmDto
import ru.aasmc.filmservice.dto.GenreDto
import ru.aasmc.filmservice.exceptions.ErrorResponse
import ru.aasmc.filmservice.service.GenreService

@RestController
@RequestMapping("/genres")
class GenreController(
    private val service: GenreService
) {

    @Operation(summary = "Retrieves a list of all film genres stored on the server.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = GenreDto::class))
                )]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<GenreDto> {
        return service.getAll()
    }

    @Operation(summary = "Retrieves a film genre by its ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = GenreDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Film genre is not found",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                )]
        )
    ])
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable("id") id: Int): GenreDto {
        return service.getById(id)
    }

}