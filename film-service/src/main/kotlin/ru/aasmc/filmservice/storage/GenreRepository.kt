package ru.aasmc.filmservice.storage

import org.springframework.data.jpa.repository.JpaRepository
import ru.aasmc.filmservice.model.Genre

interface GenreRepository: JpaRepository<Genre, Int> {
}