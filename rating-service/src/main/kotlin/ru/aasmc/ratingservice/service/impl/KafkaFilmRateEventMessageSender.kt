package ru.aasmc.ratingservice.service.impl

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.aasmc.ratingservice.config.KafkaProps
import ru.aasmc.ratingservice.dto.FilmRateDto
import ru.aasmc.ratingservice.service.FilmRateEventMessageSender

private val log = LoggerFactory.getLogger(KafkaFilmRateEventMessageSender::class.java)

@Service
class KafkaFilmRateEventMessageSender(
        private val kafkaTemplate: KafkaTemplate<String, FilmRateDto>,
        private val kafkaProps: KafkaProps
) : FilmRateEventMessageSender {
    override fun sendFilmRate(dto: FilmRateDto) {
        val send = kafkaTemplate.send(kafkaProps.newRatingTopic, dto)
        send.addCallback(
                { _ ->
                    log.info(
                            "Successfully sent message {} to kafka topic: {}",
                            dto,
                            kafkaProps.newRatingTopic
                    )
                },
                { ex ->
                    log.error(
                            "Failed to send message {} to kafka topic: {}",
                            dto,
                            kafkaProps.newRatingTopic
                    )
                }
        )
    }
}