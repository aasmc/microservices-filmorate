package ru.aasmc.userservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.aasmc.userservice.dto.CommonEventDto

@FeignClient("event-service")
interface EventsClient {

    @GetMapping("/events/{userId}/feed")
    fun getEventsForUser(@PathVariable("userId") userId: Long): List<CommonEventDto>

}