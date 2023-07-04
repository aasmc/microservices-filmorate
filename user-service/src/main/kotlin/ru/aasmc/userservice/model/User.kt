package ru.aasmc.userservice.model

import java.time.LocalDate

data class User(
        val email: String,
        val login: String,
        val name: String,
        val birthDay: LocalDate,
        val id: Long? = null
)
