package ru.aasmc.eventservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.aasmc.eventservice.model.Event

interface EventRepository: JpaRepository<Event, Long> {

    fun findAllByUserIdEqualsOrderByTimestamp(userId: Long): List<Event>

    fun deleteAllByUserIdEquals(userId: Long)

}