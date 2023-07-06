package ru.aasmc.ratingservice.service.impl

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import ru.aasmc.ratingservice.appevents.DeleteAllLikesEvent
import ru.aasmc.ratingservice.appevents.FilmLikeEvent
import ru.aasmc.ratingservice.dto.DeleteAllLikesDto
import ru.aasmc.ratingservice.dto.FilmLikeDto
import ru.aasmc.ratingservice.service.KafkaEventListener

private val log = LoggerFactory.getLogger(KafkaEventListenerImpl::class.java)

@Service
class KafkaEventListenerImpl (
        private val applicationEventPublisher: ApplicationEventPublisher
): KafkaEventListener {

    @KafkaListener(
            topics = ["\${kafkaprops.filmLikeTopic}"],
            groupId = "\${spring.application.name}",
            autoStartup = "true"
    )
    override fun consumeFilmLike(record: ConsumerRecord<String, FilmLikeDto>) {
        val dto = record.value()
        log.info("Consuming message {} from Kafka topic {}", dto, record.topic())
        applicationEventPublisher.publishEvent(
                FilmLikeEvent(
                        source = this,
                        dto = dto
                )
        )
    }

    @KafkaListener(
            topics = ["\${kafkaprops.deleteAllLikesTopic}"],
            groupId = "\${spring.application.name}",
            autoStartup = "true"
    )
    override fun consumeDeleteLikes(record: ConsumerRecord<String, DeleteAllLikesDto>) {
        val dto = record.value()
        log.info("Consuming message {} from Kafka topic {}.", dto, record.topic())
        applicationEventPublisher.publishEvent(
                DeleteAllLikesEvent(
                        source = this,
                        dto = dto
                )
        )
    }
}