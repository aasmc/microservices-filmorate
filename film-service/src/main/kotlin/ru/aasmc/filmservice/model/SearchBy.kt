package ru.aasmc.filmservice.model

enum class SearchBy(
    val columName: String
) {
    title("name"),
    director("name");
}