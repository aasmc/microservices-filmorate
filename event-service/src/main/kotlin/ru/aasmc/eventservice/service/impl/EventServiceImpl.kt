package ru.aasmc.eventservice.service.impl

import org.springframework.stereotype.Service
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.mapper.EventMapper
import ru.aasmc.eventservice.repository.EventRepository
import ru.aasmc.eventservice.service.EventService

@Service
class EventServiceImpl(
        private val eventRepository: EventRepository,
        private val mapper: EventMapper
) : EventService {

    override fun findAllEventsByUserId(userId: Long): List<CommonEventDto> {
        return eventRepository.findAllByUserIdEqualsOrderByTimestamp(userId)
                .map(mapper::mapToDto)
    }

    override fun saveEvent(event: CommonEventDto) {
        eventRepository.save(mapper.mapToDomain(event))
    }

    override fun deleteEventsForUserWithId(userId: Long) {
        eventRepository.deleteAllByUserIdEquals(userId)
    }

}