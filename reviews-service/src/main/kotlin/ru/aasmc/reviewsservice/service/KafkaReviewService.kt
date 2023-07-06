package ru.aasmc.reviewsservice.service

import ru.aasmc.reviewsservice.dto.ReviewEventDto

interface KafkaReviewService {

    fun sendToKafka(dto: ReviewEventDto)

}