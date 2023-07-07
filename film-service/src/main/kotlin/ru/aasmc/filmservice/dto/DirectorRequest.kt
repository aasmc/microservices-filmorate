package ru.aasmc.filmservice.dto

import javax.validation.constraints.NotBlank

data class DirectorRequest(
        @field:NotBlank
        val name: String,
        val id: Long? = null
)
