package ru.aasmc.filmservice.service

interface FilmRateMessageConsumer<T> {
    fun consume(record: T)
}