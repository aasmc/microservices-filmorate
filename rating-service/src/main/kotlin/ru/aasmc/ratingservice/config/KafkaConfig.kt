package ru.aasmc.ratingservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import ru.aasmc.ratingservice.dto.DeleteAllLikesDto
import ru.aasmc.ratingservice.dto.FilmLikeDto

@Configuration
class KafkaConfig(
        private val kafkaProps: KafkaProps
) {

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        val props = hashMapOf<String, Any>()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProps.bootstrapServers
        props[ConsumerConfig.GROUP_ID_CONFIG] = kafkaProps.clientId
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = kafkaProps.autoOffsetReset
        props[JsonDeserializer.USE_TYPE_INFO_HEADERS] = false
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        return props
    }

    @Bean
    fun consumerFactoryFilmLike(): ConsumerFactory<String, FilmLikeDto> {
        return DefaultKafkaConsumerFactory(
                consumerConfigs(),
                StringDeserializer(),
                JsonDeserializer(FilmLikeDto::class.java)
        )
    }

    @Bean
    fun containerFactoryFilmLike(): ConcurrentKafkaListenerContainerFactory<String, FilmLikeDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, FilmLikeDto>()
        factory.consumerFactory = consumerFactoryFilmLike()
        return factory
    }

    @Bean
    fun consumerFactoryDeleteLikes(): ConsumerFactory<String, DeleteAllLikesDto> {
        return DefaultKafkaConsumerFactory(
                consumerConfigs(),
                StringDeserializer(),
                JsonDeserializer(DeleteAllLikesDto::class.java)
        )
    }

    @Bean
    fun containerFactoryDeleteLikes(): ConcurrentKafkaListenerContainerFactory<String, DeleteAllLikesDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, DeleteAllLikesDto>()
        factory.consumerFactory = consumerFactoryDeleteLikes()
        return factory
    }

    @Bean
    fun filmRateTopic(): NewTopic {
        return TopicBuilder.name(kafkaProps.newRatingTopic)
                .partitions(1)
                .replicas(1)
                .build()
    }

}