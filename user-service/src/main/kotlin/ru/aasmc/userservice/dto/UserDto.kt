package ru.aasmc.userservice.dto

import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

data class UserDto(
        @field:Email(message = "Некорректный email")
        @field:NotEmpty(message = "email не может быть пустым")
        val email: String,
        @field:NotEmpty(message = "Логин не может быть пустым")
        @field:Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
        val login: String,
        val name: String?,
        @field:NotNull
        @field:Past(message = "Дата рождения не может быть в будущем времени")
        val birthday: LocalDate,
        val id: Long? = null
)
