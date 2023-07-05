package ru.aasmc.filmservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service-client", url = "\${urls.userService}")
interface UserServiceClient {

    @GetMapping("/users/exists/{userId}")
    fun isUserExists(@PathVariable("userId") userId: Long): Boolean

}