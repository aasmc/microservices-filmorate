package ru.aasmc.filmservice.storage

import org.springframework.data.jpa.repository.JpaRepository
import ru.aasmc.filmservice.model.Mpa

interface MpaRepository: JpaRepository<Mpa, Int> {
}