package ru.aasmc.filmservice.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.filmservice.dto.DirectorDto
import ru.aasmc.filmservice.dto.DirectorRequest
import ru.aasmc.filmservice.exceptions.BusinessServiceException
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.model.Director
import ru.aasmc.filmservice.model.mapToDto
import ru.aasmc.filmservice.service.DirectorService
import ru.aasmc.filmservice.storage.DirectorRepository

@Service
class DirectorServiceImpl(
        private val repo: DirectorRepository
) : DirectorService {
    override fun getAll(): List<DirectorDto> {
        return repo.findAll()
                .map { it.mapToDto() }
    }

    override fun getById(id: Long): DirectorDto {
        return repo.findById(id)
                .orElseThrow {
                    ResourceNotFoundException(message = "Director with ID=$id not found in DB.")
                }.mapToDto()
    }

    override fun create(request: DirectorRequest): DirectorDto {
        return repo.save(Director(name = request.name))
                .mapToDto()
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun update(request: DirectorRequest): DirectorDto {
        val id = request.id ?: throw BusinessServiceException(
                code = HttpStatus.BAD_REQUEST.value(),
                message = "Cannot update Director without ID."
        )
        checkDirectorId(id)
        repo.updateDirectorById(id, request.name)
        return DirectorDto(id, request.name)
    }

    private fun checkDirectorId(id: Long) {
        if (!repo.existsById(id)) {
            throw ResourceNotFoundException(
                    message = "Director with ID=$id not found in DB."
            )
        }
    }

    @Transactional
    override fun delete(id: Long) {
        repo.deleteDirectorById(id)
    }
}