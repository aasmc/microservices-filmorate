package ru.aasmc.userservice.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.aasmc.userservice.client.EventsClient
import ru.aasmc.userservice.dto.CommonEventDto
import ru.aasmc.userservice.error.UserServiceException
import ru.aasmc.userservice.service.EventService
import ru.aasmc.userservice.service.UserService

@Service
class EventServiceImpl(
        private val client: EventsClient,
        private val userService: UserService
) : EventService {
    override fun getEventsForUser(userId: Long): List<CommonEventDto> {
        if (!userService.isUserExists(userId)) {
            throw UserServiceException(HttpStatus.NOT_FOUND.value(), "User with ID=$userId not found.")
        }
        return client.getEventsForUser(userId)
    }
}