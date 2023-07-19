package ru.aasmc.gatewayserver.config

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class RateLimiterConfig {

    /**
     * The RequestRateLimiter filter relies on a [KeyResolver] bean to
     * determine which bucket to use for each request. By default, it uses the
     * currently authenticated user in Spring Security. Until Security is added,
     * a custom KeyResolver is defined to return a constant value, so that all
     * requests are mapped to the same bucket.
     */
    @Bean
    fun keyResolver(): KeyResolver {
        return KeyResolver { _ -> Mono.just("anonymous") }
    }
}