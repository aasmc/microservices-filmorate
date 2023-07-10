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
                description = "List of all film directors.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = (
                                    ArraySchema(
                                            schema = Schema(
                                                    implementation = DirectorDto::class
                                            )
                                         )
                                    )
                    )
                ]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<DirectorDto> {
        return service.getAll()
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable("id") id: Long): DirectorDto {
        return service.getById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: DirectorRequest): DirectorDto {
        return service.create(request)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody @Valid request: DirectorRequest): DirectorDto {
        return service.update(request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        service.delete(id)
    }

}