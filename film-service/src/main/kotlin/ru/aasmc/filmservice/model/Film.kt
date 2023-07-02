package ru.aasmc.filmservice.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "FILMS")
class Film(
    var name: String,
    var description: String,
    @Column(name = "release_date")
    var releaseDate: LocalDate,
    var duration: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RATING_ID", nullable = false)
    var mpa: Mpa,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "FILM_GENRE",
        joinColumns = [JoinColumn(name = "FILM_ID", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "GENRE_ID")]
    )
    var genres: MutableSet<Genre> = hashSetOf(),
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "FILM_DIRECTOR",
        joinColumns = [JoinColumn(name = "FILM_ID", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "DIRECTOR_ID", nullable = false)]
    )
    var directors: MutableSet<Director> = hashSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null
)