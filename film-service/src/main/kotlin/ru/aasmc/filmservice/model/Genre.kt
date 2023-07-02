package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.GenreDto
import javax.persistence.*

@Entity
@Table(name = "GENRES")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "genre_sequence", allocationSize = 1)
class Genre(
    var name: String,
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    var id: Int? = null
)

fun Genre.mapToDto(): GenreDto = GenreDto(
    id = id ?: throw IllegalStateException("Called mapToDto on Genre without ID."),
    name = name
)
