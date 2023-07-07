package ru.aasmc.ratingservice.service

import ru.aasmc.ratingservice.dto.FilmRateDto

interface FilmRateEventMessageSender {

    fun sendFilmRate(dto: FilmRateDto)

}