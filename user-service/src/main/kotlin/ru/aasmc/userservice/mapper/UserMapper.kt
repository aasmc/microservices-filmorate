package ru.aasmc.userservice.mapper

import org.springframework.stereotype.Component
import ru.aasmc.userservice.dto.UserDto
import ru.aasmc.userservice.model.User
import ru.aasmc.userservice.util.Mapper

@Component
class UserMapper : Mapper<User, UserDto> {

    override fun mapToDomain(dto: UserDto): User {
        return User(
                email = dto.email,
                login = dto.login,
                name = if (dto.name.isNullOrBlank()) {
                    dto.login
                } else {
                    dto.name
                },
                birthDay = dto.birthday,
                id = dto.id
        )
    }

    override fun mapToDto(domain: User): UserDto {
        return UserDto(
                email = domain.email,
                login = domain.login,
                name = domain.name,
                birthday = domain.birthDay,
                id = domain.id
        )
    }
}