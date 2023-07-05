package ru.aasmc.userservice.util

interface Mapper<Domain, Dto> {
    fun mapToDomain(dto: Dto): Domain

    fun mapToDto(domain: Domain): Dto
}