package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.MpaDto
import javax.persistence.*

@Entity
@Table(name = "RATING")
class Mpa(
    var name: String,
    @OneToMany(mappedBy = "mpa", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null
)

fun Mpa.mapToDto(): MpaDto = MpaDto(
    mpaId = id ?: throw IllegalStateException("Called mapToDto() on Mpa entity without ID."),
    name = name
)