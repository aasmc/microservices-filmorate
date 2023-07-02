package ru.aasmc.filmservice.model.mapper

interface Mapper<Domain, Request, Response> {
    fun mapToDomain(dto: Request): Domain

    fun mapToDto(domain: Domain): Response
}