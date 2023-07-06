package ru.aasmc.reviewsservice.model

import javax.persistence.*

@Entity
@Table(name = "REVIEWS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "film_sequence", allocationSize = 1)
class Review(
        var content: String,
        @Column(name = "IS_POSITIVE")
        var isPositive: Boolean,
        @Column(name = "USER_ID")
        var userId: Long,
        @Column(name = "FILM_ID")
        var filmId: Long,
        var useful: Int = 0,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        var id: Long? = null
)