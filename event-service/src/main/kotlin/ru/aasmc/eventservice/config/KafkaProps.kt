package ru.aasmc.eventservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kafkaprops")
class KafkaProps @ConstructorBinding constructor(
        var filmLikeTopic: String,
        var reviewsTopic: String,
        var userFriendTopic: String,
        var bootstrapServers: String,
        var clientId: String,
        var autoOffsetReset: String,
)
