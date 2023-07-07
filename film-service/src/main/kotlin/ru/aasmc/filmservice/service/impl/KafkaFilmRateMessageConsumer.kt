package ru.aasmc.filmservice.service.impl

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import ru.aasmc.filmservice.appevents.FilmRateEvent
import ru.aasmc.filmservice.dto.FilmRateDto
import ru.aasmc.filmservice.service.FilmRateMessageConsumer

private val log = LoggerFactory.getLogger(KafkaFilmRateMessageConsumer::class.java)

@Service
class KafkaFilmRateMessageConsumer(
        private val applicationEventPublisher: ApplicationEventPublisher
) : FilmRateMessageConsumer<ConsumerRecord<String, FilmRateDto>> {
    override fun consume(record: ConsumerRecord<String, FilmRateDto>) {
        val dto = record.value()
        log.info(
                "Consuming message from kafka topic {}. Message {}",
                record.topic(),
                dto
        )
        applicationEventPublisher.publishEvent(
                FilmRateEvent(
                        source = this,
                        filmId = dto.filmId,
                        rate = dto.rate
                )
        )
    }
}