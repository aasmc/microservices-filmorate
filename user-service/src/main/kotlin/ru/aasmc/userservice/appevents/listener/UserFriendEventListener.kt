package ru.aasmc.userservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.aasmc.userservice.appevents.UserFriendEvent
import ru.aasmc.userservice.dto.UserFriendEventDto
import ru.aasmc.userservice.service.UserFriendKafkaService

private val log = LoggerFactory.getLogger(UserFriendEventListener::class.java)

@Component
class UserFriendEventListener (
        private val service: UserFriendKafkaService
){

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserFriendEvent(event: UserFriendEvent) {
        log.info("Handling User Friend Event: {}", event)
        service.sendEventToKafka(UserFriendEventDto(
                timestamp = event.timeStamp,
                userId = event.userId,
                friendId = event.friendId,
                operation = event.operation
        ))
    }

}