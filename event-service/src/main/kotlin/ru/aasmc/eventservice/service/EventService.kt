package ru.aasmc.eventservice.service

import ru.aasmc.eventservice.dto.CommonEventDto

interface EventService {

    fun findAllEventsByUserId(userId: Long): List<CommonEventDto>

    fun saveEvent(event: CommonEventDto)

    fun deleteEventsForUserWithId(userId: Long)

}