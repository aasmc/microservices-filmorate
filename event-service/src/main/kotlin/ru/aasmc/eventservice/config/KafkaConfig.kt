package ru.aasmc.eventservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import ru.aasmc.eventservice.dto.FilmLikeDto
import ru.aasmc.eventservice.dto.ReviewEventDto
import ru.aasmc.eventservice.dto.UserFriendEventDto

@Configuration
class KafkaConfig(
        private val kafkaProps: KafkaProps
) {

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        return hashMapOf(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProps.bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to kafkaProps.clientId,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaProps.autoOffsetReset,
                JsonDeserializer.USE_TYPE_INFO_HEADERS to false,
                JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
    }

    @Bean
    fun consumerFactoryUser(): ConsumerFactory<String, UserFriendEventDto> {
        return DefaultKafkaConsumerFactory(
                consumerConfigs(),
                StringDeserializer(),
                JsonDeserializer(UserFriendEventDto::class.java)
        )
    }

    @Bean
    fun containerFactoryUser(): ConcurrentKafkaListenerContainerFactory<String, UserFriendEventDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, UserFriendEventDto>()
        factory.consumerFactory = consumerFactoryUser()
        return factory
    }

    @Bean
    fun consumerFactoryReview(): ConsumerFactory<String, ReviewEventDto> {
        return DefaultKafkaConsumerFactory(
                consumerConfigs(),
                StringDeserializer(),
                JsonDeserializer(ReviewEventDto::class.java)
        )
    }

    @Bean
    fun containerFactoryReview(): ConcurrentKafkaListenerContainerFactory<String, ReviewEventDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ReviewEventDto>()
        factory.consumerFactory = consumerFactoryReview()
        return factory
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
}