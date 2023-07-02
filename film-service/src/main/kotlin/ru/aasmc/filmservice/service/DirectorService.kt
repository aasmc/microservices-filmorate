package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.DirectorDto
import ru.aasmc.filmservice.dto.DirectorRequest

interface DirectorService {
    fun getAll(): List<DirectorDto>
    fun getById(id: Long): DirectorDto
    fun create(request: DirectorRequest): DirectorDto
    fun update(request: DirectorRequest): DirectorDto
    fun delete(id: Long)
}