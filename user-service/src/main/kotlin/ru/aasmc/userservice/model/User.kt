package ru.aasmc.userservice.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "USERS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "user_sequence", allocationSize = 1)
class User(
        var email: String,
        var login: String,
        var name: String,
        @Column(name = "BIRTHDAY")
        var birthDay: LocalDate,
        @ManyToMany
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
