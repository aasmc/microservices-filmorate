package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.DirectorDto
import javax.persistence.*

@Entity
@Table(name = "DIRECTORS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "director_sequence", allocationSize = 1)
class Director(
    var name: String,
    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    var id: Long? = null
)

fun Director.mapToDto(): DirectorDto = DirectorDto(
    id = id ?: throw IllegalStateException("Called mapToDto on Director without ID."),
    name = name
)