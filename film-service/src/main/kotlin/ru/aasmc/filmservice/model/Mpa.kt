package ru.aasmc.filmservice.model

import ru.aasmc.filmservice.dto.MpaDto
import javax.persistence.*

@Entity
@Table(name = "RATING")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "mpa_sequence", allocationSize = 1)
class Mpa(
    var name: String,
    @OneToMany(mappedBy = "mpa", fetch = FetchType.LAZY)
    var films: MutableSet<Film> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    var id: Int? = null
)

fun Mpa.mapToDto(): MpaDto = MpaDto(
    id = id ?: throw IllegalStateException("Called mapToDto() on Mpa entity without ID."),
    name = name
)