package ru.aasmc.filmservice.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.GenreDto
import ru.aasmc.filmservice.service.GenreService

@RestController
@RequestMapping("/genres")
class GenreController(
    private val service: GenreService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<GenreDto> {
        return service.getAll()
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable("id") id: Int): GenreDto {
        return service.getById(id)
    }

}