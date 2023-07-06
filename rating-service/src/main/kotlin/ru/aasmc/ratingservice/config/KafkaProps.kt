package ru.aasmc.ratingservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kafkaprops")
class KafkaProps @ConstructorBinding constructor (
        var filmLikeTopic: String,
        var deleteAllLikesTopic: String
)