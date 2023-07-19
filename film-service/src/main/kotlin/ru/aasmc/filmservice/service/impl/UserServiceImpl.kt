package ru.aasmc.filmservice.service.impl

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import ru.aasmc.filmservice.client.UserServiceClient
import ru.aasmc.filmservice.service.UserService

@Service
class UserServiceImpl(
        private val userServiceClient: UserServiceClient
) : UserService {

    @CircuitBreaker(name = "userClient")
    @Bulkhead(name = "userClientBulkhead", type = Bulkhead.Type.THREADPOOL)
    @Retry(name = "retryUserClient")
    @RateLimiter(name = "userClientRateLimiter")
    override fun isUserExists(userId: Long): Boolean {
        return userServiceClient.isUserExists(userId)
    }
}