package ru.aasmc.userservice.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "USERS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "user_sequence", allocationSize = 1)
class User(
        var email: String,
        var login: String,
        var name: String,
        @Column(name = "BIRTHDAY")
        var birthDay: LocalDate,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "FRIENDS",
                joinColumns = [JoinColumn(name = "USER_ID")],
                inverseJoinColumns = [JoinColumn(name = "FRIEND_ID")]
        )
        var friends: MutableList<User> = arrayListOf(),
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
        @Column(name = "USER_ID")
        var id: Long? = null
)
