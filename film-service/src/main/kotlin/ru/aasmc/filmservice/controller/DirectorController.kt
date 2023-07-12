package ru.aasmc.filmservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.DirectorDto
import ru.aasmc.filmservice.dto.DirectorRequest
import ru.aasmc.filmservice.exceptions.ErrorResponse
import ru.aasmc.filmservice.service.DirectorService
import javax.validation.Valid

@RestController
@RequestMapping("/directors")
class DirectorController(
        private val service: DirectorService
) {
    @Operation(summary = "Get a list of all film directors.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = DirectorDto::class)))
                )]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<DirectorDto> {
        return service.getAll()
    }


    @Operation(summary = "Get film director by ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = DirectorDto::class))
                ]),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find director by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable("id") id: Long): DirectorDto {
        return service.getById(id)
    }


    @Operation(summary = "Creates a film director if the request is valid.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "201",
                description = "Successfully created film director.",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = DirectorDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: DirectorRequest): DirectorDto {
        return service.create(request)
    }

    @Operation(summary = "Updating film director.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully updated film director",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = DirectorDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find director by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody @Valid request: DirectorRequest): DirectorDto {
        return service.update(request)
    }

    @Operation(summary = "Delete film director by id")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Successfully deleted film director."
        )
    ])
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        service.delete(id)
    }

}