package ru.aasmc.eventservice.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import ru.aasmc.eventservice.dto.FilmLikeDto
import ru.aasmc.eventservice.dto.ReviewEventDto
import ru.aasmc.eventservice.dto.UserFriendEventDto

interface KafkaEventListener {

    fun consumeUserEvent(record: ConsumerRecord<String, UserFriendEventDto>)

    fun consumeFilmLikeEvent(record: ConsumerRecord<String, FilmLikeDto>)

    fun consumeReviewEvent(record: ConsumerRecord<String, ReviewEventDto>)

    fun consumeDeleteUser(record: ConsumerRecord<String, Long>)

}