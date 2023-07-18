package ru.aasmc.filmservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("user-service")
interface UserServiceClient {

    @GetMapping("/users/exists/{userId}")
    fun isUserExists(@PathVariable("userId") userId: Long): Boolean

}