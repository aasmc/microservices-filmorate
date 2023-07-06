package ru.aasmc.reviewsservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kafkaprops")
class KafkaProps @ConstructorBinding constructor(
        var reviewsTopic: String
) {
}