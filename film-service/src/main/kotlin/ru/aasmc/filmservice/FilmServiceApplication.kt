package ru.aasmc.filmservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FilmServiceApplication

fun main(args: Array<String>) {
    runApplication<FilmServiceApplication>(*args)
}
