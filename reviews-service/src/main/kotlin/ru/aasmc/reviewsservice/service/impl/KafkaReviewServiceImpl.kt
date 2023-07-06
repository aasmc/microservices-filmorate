package ru.aasmc.reviewsservice.service.impl

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.aasmc.reviewsservice.config.KafkaProps
import ru.aasmc.reviewsservice.dto.ReviewEventDto
import ru.aasmc.reviewsservice.service.KafkaReviewService

private val log = LoggerFactory.getLogger(KafkaReviewServiceImpl::class.java)

@Service
class KafkaReviewServiceImpl(
        private val kafkaTemplate: KafkaTemplate<String, ReviewEventDto>,
        private val kafkaProps: KafkaProps
) : KafkaReviewService {
    override fun sendToKafka(dto: ReviewEventDto) {
        val send = kafkaTemplate.send(
                kafkaProps.reviewsTopic,
                dto
        )

        send.addCallback(
                { _ ->
                    log.info("Successfully sent Review Event {} to kafka.", dto)
                },
                { ex ->
                    log.error("Failed to send Review Event {} to kafka. Error message: {}", dto, ex.message)
                }
        )
    }
}