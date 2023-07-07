package ru.aasmc.ratingservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("filmLikes")
class FilmLike(
        @Id
        var id: FilmLikeId?,
        var timestamp: Long?,
        var mark: Int?
)

class FilmLikeId {
    var filmId: Long = 0
    var userId: Long = 0
}