package ru.aasmc.userservice.dto

import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

data class UserDto(
        @Email(message = "Некорректный email")
        @NotEmpty(message = "email не может быть пустым")
        val email: String,
        @NotEmpty(message = "Логин не может быть пустым")
        @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
        val login: String,
        val name: String?,
        @NotNull
        @Past(message = "Дата рождения не может быть в будущем времени")
        val birthDay: LocalDate,
        val id: Long? = null
)
