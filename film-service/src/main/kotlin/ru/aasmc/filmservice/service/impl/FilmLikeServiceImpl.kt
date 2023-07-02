package ru.aasmc.filmservice.service.impl

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.aasmc.filmservice.dto.DeleteAllLikesDto
import ru.aasmc.filmservice.dto.EventOperation
import ru.aasmc.filmservice.dto.FilmLikeDto
import ru.aasmc.filmservice.kafka.KafkaProps
import ru.aasmc.filmservice.service.FilmLikeService
import java.time.Instant

private val log = LoggerFactory.getLogger(FilmLikeServiceImpl::class.java)
@Service
class FilmLikeServiceImpl(
    private val singleLikeKafkaTemplate: KafkaTemplate<String, FilmLikeDto>,
    private val deleteAllKafkaTemplate: KafkaTemplate<String, DeleteAllLikesDto>,
    private val kafkaProps: KafkaProps
) : FilmLikeService {
    override fun addLike(filmId: Long, userId: Long, mark: Int) {
        val filmLike = FilmLikeDto(
            timestamp = Instant.now().toEpochMilli(),
            userId = userId,
            operation = EventOperation.ADD,
            mark = mark,
            filmId = filmId
        )
        sendToKafka(filmLike)
    }

    override fun removeLike(filmId: Long, userId: Long) {
        val filmLike = FilmLikeDto(
            timestamp = Instant.now().toEpochMilli(),
            userId = userId,
            operation = EventOperation.REMOVE,
            filmId = filmId
        )
        sendToKafka(filmLike)
    }

    override fun deleteAllLikes(filmId: Long, timestamp: Long) {
        val deleteLikesDto = DeleteAllLikesDto(
            filmId = filmId,
            timestamp = timestamp
        )
        val send = deleteAllKafkaTemplate.send(
            kafkaProps.deleteAllLikesTopic,
            deleteLikesDto
        )
        send.addCallback(
            { _ ->
                log.info("Successfully sent DeleteAllLikesDto: $deleteLikesDto to topic: ${kafkaProps.deleteAllLikesTopic}")
            },
            { error ->
                log.error("Failed to send DeleteAllLikesDto: $deleteLikesDto to topic: ${kafkaProps.deleteAllLikesTopic}. Error: ${error.message}")
            }
        )
    }

    private fun sendToKafka(filmLike: FilmLikeDto) {
        val send = singleLikeKafkaTemplate.send(
            kafkaProps.filmLikeTopic,
            filmLike
        )
        send.addCallback(
            { _ ->
                log.info("Successfully sent FilmLikeDto: $filmLike to topic: ${kafkaProps.filmLikeTopic}")
            },
            { error ->
                log.error("Failed to send FilmLikeDto: $filmLike to topic: ${kafkaProps.filmLikeTopic}. Error: ${error.message}")
            }
        )
    }
}