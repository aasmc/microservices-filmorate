package ru.aasmc.reviewsservice.mapper

interface Mapper<Domain, Dto> {
    fun mapToDomain(dto: Dto): Domain

    fun mapToDto(domain: Domain): Dto
}