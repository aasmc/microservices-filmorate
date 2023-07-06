package ru.aasmc.ratingservice.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import ru.aasmc.ratingservice.dto.DeleteAllLikesDto
import ru.aasmc.ratingservice.dto.FilmLikeDto

interface KafkaEventListener {
    fun consumeFilmLike(record: ConsumerRecord<String, FilmLikeDto>)

    fun consumeDeleteLikes(record: ConsumerRecord<String, DeleteAllLikesDto>)

}