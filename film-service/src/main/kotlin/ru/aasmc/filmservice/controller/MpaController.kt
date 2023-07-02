package ru.aasmc.filmservice.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.filmservice.dto.MpaDto
import ru.aasmc.filmservice.service.MpaService

@RestController
@RequestMapping("/mpa")
class MpaController (
    private val service: MpaService
){
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<MpaDto> {
        return service.getAll()
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable("id") id: Int): MpaDto {
        return service.getById(id)
    }
}