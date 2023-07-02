package ru.aasmc.filmservice.service

import ru.aasmc.filmservice.dto.MpaDto

interface MpaService {
    fun getById(id: Int): MpaDto

    fun getAll(): List<MpaDto>
}