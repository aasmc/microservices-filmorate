package ru.aasmc.eventservice.mapper

interface Mapper<Domain, Dto> {

    fun mapToDto(domain: Domain): Dto

    fun mapToDomain(dto: Dto): Domain

}