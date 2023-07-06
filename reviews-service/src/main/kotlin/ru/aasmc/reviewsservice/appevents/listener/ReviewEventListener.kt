package ru.aasmc.reviewsservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.aasmc.reviewsservice.appevents.ReviewEvent
import ru.aasmc.reviewsservice.dto.ReviewEventDto
import ru.aasmc.reviewsservice.service.KafkaReviewService

private val log = LoggerFactory.getLogger(ReviewEventListener::class.java)

@Component
class ReviewEventListener(
        private val kafkaService: KafkaReviewService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun consumeEvent(event: ReviewEvent) {
        log.info("Received event {}", event)
        kafkaService.sendToKafka(ReviewEventDto(
                timestamp = event.timeStamp,
                reviewId = event.reviewId,
                operation = event.operation,
                userId = event.userId
        ))
    }

}