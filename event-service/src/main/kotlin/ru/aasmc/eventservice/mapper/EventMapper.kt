package ru.aasmc.eventservice.mapper

import org.springframework.stereotype.Component
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.model.Event

@Component
class EventMapper: Mapper<Event, CommonEventDto> {

    override fun mapToDto(domain: Event): CommonEventDto {
        return CommonEventDto(
                eventId = domain.id,
                timestamp = domain.timestamp,
                userId = domain.userId,
                eventType = domain.eventType,
                operation = domain.operation,
                entityId = domain.entityId
        )
    }

    override fun mapToDomain(dto: CommonEventDto): Event {
        return Event(
                id = dto.eventId,
                timestamp = dto.timestamp,
                userId = dto.userId,
                eventType = dto.eventType,
                operation = dto.operation,
                entityId = dto.entityId
        )
    }

}