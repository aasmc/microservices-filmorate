package ru.aasmc.filmservice.service.impl

import org.springframework.stereotype.Service
import ru.aasmc.filmservice.dto.GenreDto
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.model.mapToDto
import ru.aasmc.filmservice.service.GenreService
import ru.aasmc.filmservice.storage.GenreRepository

@Service
class GenreServiceImpl(
    private val repo: GenreRepository
) : GenreService {
    override fun getAll(): List<GenreDto> {
        return repo.findAll().map { it.mapToDto() }
    }

    override fun getById(id: Int): GenreDto {
        return repo.findById(id)
            .orElseThrow {
                ResourceNotFoundException(message = "Genre with ID=$id not found in DB.")
            }.mapToDto()
    }
}