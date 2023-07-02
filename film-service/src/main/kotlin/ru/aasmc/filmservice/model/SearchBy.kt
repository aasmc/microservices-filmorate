package ru.aasmc.filmservice.model

enum class SearchBy(
    val columName: String
) {
    title("f.name"),
    director("d.name");

    fun toSql(): String =
        " $columName LIKE CONCAT('%',?1,'%') "
}