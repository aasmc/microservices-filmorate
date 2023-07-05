package ru.aasmc.userservice.service

import ru.aasmc.userservice.dto.UserFriendEventDto

interface UserFriendKafkaService {

    fun sendEventToKafka(dto: UserFriendEventDto)

}