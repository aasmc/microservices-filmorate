package ru.aasmc.eventservice.service.impl

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import ru.aasmc.eventservice.appevents.FilmLikeEvent
import ru.aasmc.eventservice.appevents.ReviewEvent
import ru.aasmc.eventservice.appevents.UserFriendEvent
import ru.aasmc.eventservice.dto.FilmLikeDto
import ru.aasmc.eventservice.dto.ReviewEventDto
import ru.aasmc.eventservice.dto.UserFriendEventDto
import ru.aasmc.eventservice.service.KafkaEventListener

private val log = LoggerFactory.getLogger(KafkaEventListenerImpl::class.java)

@Service
class KafkaEventListenerImpl(
        private val applicationEventPublisher: ApplicationEventPublisher
) : KafkaEventListener {

    @KafkaListener(
            topics = ["\${kafkaprops.userFriendTopic}"],
            groupId = "\${spring.application.name}",
            autoStartup = "true",
            containerFactory = "containerFactoryUser"
    )
    override fun consumeUserEvent(record: ConsumerRecord<String, UserFriendEventDto>) {
        val dto = record.value()
        log.info("Consuming UserFriend event: {} from kafka topic: {}", dto, record.topic())
        applicationEventPublisher.publishEvent(
                UserFriendEvent(
                        source = this,
                        dto = dto
                )
        )
    }

    @KafkaListener(
            topics = ["\${kafkaprops.filmLikeTopic}"],
            groupId = "\${spring.application.name}",
            autoStartup = "true",
            containerFactory = "containerFactoryFilmLike"
    )
    override fun consumeFilmLikeEvent(record: ConsumerRecord<String, FilmLikeDto>) {
        val dto = record.value()
        log.info("Consuming FilmLike event: {}, from kafka topic: {}", dto, record.topic())
        applicationEventPublisher.publishEvent(
                FilmLikeEvent(
                        source = this,
                        dto = dto
                )
        )
    }

    @KafkaListener(
            topics = ["\${kafkaprops.reviewsTopic}"],
            groupId = "\${spring.application.name}",
            autoStartup = "true",
            containerFactory = "containerFactoryReview"
    )
    override fun consumeReviewEvent(record: ConsumerRecord<String, ReviewEventDto>) {
        val dto = record.value()
        log.info("Consuming Review Event: {}, from kafka topic: {}", dto, record.topic())
        applicationEventPublisher.publishEvent(
                ReviewEvent(
                        source = this,
                        dto = dto
                )
        )
    }

}