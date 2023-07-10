package ru.aasmc.eventservice.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.mapper.EventMapper
import ru.aasmc.eventservice.repository.EventRepository
import ru.aasmc.eventservice.service.EventService

@Service
@Transactional
class EventServiceImpl(
        private val eventRepository: EventRepository,
        private val mapper: EventMapper
) : EventService {

    override fun findAllEventsByUserId(userId: Long): List<CommonEventDto> {
        val result = eventRepository.findAllByUserIdEqualsOrderByTimestamp(userId)
                .map(mapper::mapToDto)
        return result
    }

    override fun saveEvent(event: CommonEventDto) {
        eventRepository.save(mapper.mapToDomain(event))
    }

    override fun deleteEventsForUserWithId(userId: Long) {
        eventRepository.deleteAllByUserIdEquals(userId)
    }

}