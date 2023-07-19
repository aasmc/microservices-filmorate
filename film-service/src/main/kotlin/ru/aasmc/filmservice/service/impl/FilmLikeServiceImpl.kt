package ru.aasmc.filmservice.service.impl

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.filmservice.client.UserServiceClient
import ru.aasmc.filmservice.dto.DeleteAllLikesDto
import ru.aasmc.filmservice.dto.EventOperation
import ru.aasmc.filmservice.dto.FilmLikeDto
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException
import ru.aasmc.filmservice.kafka.KafkaProps
import ru.aasmc.filmservice.service.FilmLikeService
import ru.aasmc.filmservice.service.FilmService
import java.time.Instant

private val log = LoggerFactory.getLogger(FilmLikeServiceImpl::class.java)

@Service
@Transactional
class FilmLikeServiceImpl(
        private val singleLikeKafkaTemplate: KafkaTemplate<String, FilmLikeDto>,
        private val deleteAllKafkaTemplate: KafkaTemplate<String, DeleteAllLikesDto>,
        private val kafkaProps: KafkaProps,
        private val filmService: FilmService,
        private val userClient: UserServiceClient
) : FilmLikeService {
    override fun addLike(filmId: Long, userId: Long, mark: Int) {
        checkFilmId(filmId)
        checkUserId(userId)
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
        checkFilmId(filmId)
        checkUserId(userId)
        val filmLike = FilmLikeDto(
                timestamp = Instant.now().toEpochMilli(),
                userId = userId,
                operation = EventOperation.REMOVE,
                filmId = filmId
        )
        sendToKafka(filmLike)
    }

    override fun deleteAllLikes(filmId: Long, timestamp: Long) {
        checkFilmId(filmId)
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

    private fun checkFilmId(filmId: Long) {
        if (!filmService.userExists(filmId)) {
            throw ResourceNotFoundException(message = "Film with ID=$filmId not found in DB.")
        }
    }

    @CircuitBreaker(name = "userClient")
    @Bulkhead(name = "userClientBulkhead", type = Bulkhead.Type.THREADPOOL)
    fun checkUserId(userId: Long) {
        if (!userClient.isUserExists(userId)) {
            throw ResourceNotFoundException(message = "User with ID=$userId not found in DB.")
        }
    }
}