package ru.aasmc.userservice.service.impl

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.aasmc.userservice.config.KafkaProps
import ru.aasmc.userservice.dto.UserFriendEventDto
import ru.aasmc.userservice.service.UserFriendKafkaService

private val log = LoggerFactory.getLogger(UserFriendKafkaServiceImpl::class.java)

@Service
class UserFriendKafkaServiceImpl(
        private val kafkaTemplate: KafkaTemplate<String, UserFriendEventDto>,
        private val kafkaProps: KafkaProps
) : UserFriendKafkaService {
    override fun sendEventToKafka(dto: UserFriendEventDto) {
        val send = kafkaTemplate.send(
                kafkaProps.userFriendTopic,
                dto
        )
        send.addCallback(
                { _ ->
                    log.info("Successfully sent $dto to topic: ${kafkaProps.userFriendTopic}")
                },
                { error ->
                    log.error("Failed to send $dto to topic: ${kafkaProps.userFriendTopic}. Error message: ${error.message}")
                }
        )
    }
}