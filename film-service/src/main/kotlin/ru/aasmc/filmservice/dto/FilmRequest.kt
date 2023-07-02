package ru.aasmc.filmservice.dto

import ru.aasmc.filmservice.validation.ReleaseDateCorrect
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class FilmRequest(
        @NotBlank(message = "Название не может быть пустым.")
        val name: String,
        @Size(max = 200, message = "Максимальная длина описания — 200 символов.")
        val description: String,
        @ReleaseDateCorrect(message = "Дата релиза — не раньше 28 декабря 1895 года.")
        val releaseDate: LocalDate,
        @Positive(message = "Продолжительность фильма должна быть положительной.")
        val duration: Int,
        val mpa: MpaDto,
        val genres: Set<GenreDto> = hashSetOf(),
        val directors: Set<DirectorDto> = hashSetOf(),
        val id: Long? = null
)
