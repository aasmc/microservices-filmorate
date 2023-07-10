package ru.aasmc.filmservice.dto

import io.swagger.v3.oas.annotations.media.Schema

data class DirectorDto(
        @field:Schema(
                description = "ID of film director.",
                type = "long"
        )
        val id: Long,
        @field:Schema(
                description = "Name of film director.",
                example = "Alfred Hitchcock"
        )
        val name: String?
)
