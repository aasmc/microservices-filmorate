package ru.aasmc.filmservice.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class TopicConfig (
    private val kafkaProps: KafkaProps
){

    @Bean
    fun filmLikeTopic(): NewTopic {
        return TopicBuilder.name(kafkaProps.filmLikeTopic)
            .partitions(1)
            .replicas(1)
            .build()
    }

    @Bean
    fun deleteAllLikesTopic(): NewTopic {
        return TopicBuilder.name(kafkaProps.deleteAllLikesTopic)
            .partitions(1)
            .replicas(1)
            .build()
    }

}