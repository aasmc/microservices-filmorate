package ru.aasmc.ratingservice.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "LIKES")
class FilmLike(
        @EmbeddedId
        var id: FilmLikeId?,
        var timestamp: Long?,
        var mark: Double?
)

@Embeddable
class FilmLikeId: Serializable {
    @Column(name = "FILM_ID")
    var filmId: Long = 0
    @Column(name = "USER_ID")
    var userId: Long = 0
}