package ru.aasmc.eventservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.service.EventService

private val log = LoggerFactory.getLogger(EventController::class.java)

@RestController
@RequestMapping("/events")
class EventController(
        private val eventService: EventService
) {

    @Operation(summary = "Get all events for user by his/her ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Events for User",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = (
                                    ArraySchema(
                                            schema = Schema(
                                                implementation = CommonEventDto::class
                                            ))
                                    )
                    )
                ]
        )
    ])
    @GetMapping("/{userId}/feed")
    @ResponseStatus(HttpStatus.OK)
    fun getEventsForUser(@PathVariable("userId") userId: Long): List<CommonEventDto> {
        log.info("Received request to GET events for user with ID={}", userId)
        return eventService.findAllEventsByUserId(userId)
    }

}