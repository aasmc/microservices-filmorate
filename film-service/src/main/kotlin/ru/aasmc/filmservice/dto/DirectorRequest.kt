package ru.aasmc.filmservice.dto

data class DirectorRequest(
    val name: String,
    val directorId: Long? = null
)
