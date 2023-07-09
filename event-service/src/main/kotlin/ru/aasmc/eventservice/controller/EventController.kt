package ru.aasmc.eventservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.service.EventService

private val log = LoggerFactory.getLogger(EventController::class.java)

@RestController
@RequestMapping("/events")
class EventController(
        private val eventService: EventService
) {

    @GetMapping("/{userId}/feed")
    fun getEventsForUser(@PathVariable("userId") userId: Long): List<CommonEventDto> {
        log.info("Received request to GET events for user with ID={}", userId)
        return eventService.findAllEventsByUserId(userId)
    }

}