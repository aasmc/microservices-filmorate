package ru.aasmc.filmservice.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "FILMS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "film_sequence", allocationSize = 1)
class Film(
        var name: String,
        var description: String,
        @Column(name = "RELEASE_DATE")
        var releaseDate: LocalDate,
        var duration: Int,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "RATING_ID", nullable = false)
        var mpa: Mpa,
        var rate: Double = 0.0,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "FILM_GENRE",
                joinColumns = [JoinColumn(name = "FILM_ID", nullable = false)],
                inverseJoinColumns = [JoinColumn(name = "GENRE_ID")]
        )
        var genres: MutableList<Genre> = arrayListOf(),
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "FILM_DIRECTOR",
                joinColumns = [JoinColumn(name = "FILM_ID", nullable = false)],
                inverseJoinColumns = [JoinColumn(name = "DIRECTOR_ID", nullable = false)]
        )
        var directors: MutableList<Director> = arrayListOf(),
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
        var id: Long? = null
)