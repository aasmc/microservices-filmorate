package ru.aasmc.userservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig(
        private val kafkaProps: KafkaProps
) {

    @Bean
    fun userFriendTopic(): NewTopic {
        return TopicBuilder.name(kafkaProps.userFriendTopic)
                .replicas(1)
                .partitions(1)
                .build()
    }
}