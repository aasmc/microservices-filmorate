package ru.aasmc.userservice.service

import ru.aasmc.userservice.dto.CommonEventDto

interface EventService {

    fun getEventsForUser(userId: Long): List<CommonEventDto>

}