package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.GenreDto
import javax.persistence.*

@Entity
@Table(name = "GENRES")
class Genre(
    var name: String,
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null
)

fun Genre.mapToDto(): GenreDto = GenreDto(
    genreId = id ?: throw IllegalStateException("Called mapToDto on Genre without ID."),
    name = name
)
