package ru.aasmc.ratingservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig(
        private val kafkaProps: KafkaProps
) {

    @Bean
    fun filmRateTopic(): NewTopic {
        return TopicBuilder.name(kafkaProps.newRatingTopic)
                .partitions(1)
                .replicas(1)
                .build()
    }

}