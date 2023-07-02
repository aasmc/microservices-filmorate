package ru.aasmc.filmservice.service.impl

import org.springframework.stereotype.Service
import ru.aasmc.filmservice.dto.MpaDto
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.model.mapToDto
import ru.aasmc.filmservice.service.MpaService
import ru.aasmc.filmservice.storage.MpaRepository

@Service
class MpaServiceImpl (
    private val repo: MpaRepository
): MpaService {
    override fun getById(id: Int): MpaDto {
        return repo.findById(id)
            .orElseThrow {
                ResourceNotFoundException(message = "Mpa with ID=$id not found in DB.")
            }.mapToDto()
    }

    override fun getAll(): List<MpaDto> {
        return repo.findAll()
            .map { it.mapToDto() }
    }
}