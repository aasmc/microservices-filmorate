package ru.aasmc.filmservice.dto

import javax.validation.constraints.NotEmpty

data class DirectorDto(
        val id: Long,
        @field:NotEmpty
        val name: String = ""
)
