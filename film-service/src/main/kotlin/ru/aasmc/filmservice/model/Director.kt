package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.DirectorDto
import javax.persistence.*

@Entity
@Table(name = "DIRECTORS")
class Director(
    var name: String,
    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null
)

fun Director.mapToDto(): DirectorDto = DirectorDto(
    directorId = id ?: throw IllegalStateException("Called mapToDto on Director without ID."),
    name = name
)