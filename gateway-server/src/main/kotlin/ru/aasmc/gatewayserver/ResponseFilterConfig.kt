package ru.aasmc.gatewayserver

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

private val log = LoggerFactory.getLogger(ResponseFilterConfig::class.java)

@Configuration
class ResponseFilterConfig @Autowired constructor(
        private val filterUtils: FilterUtils
) {

    /**
     * Passes necessary headers back to the client of the microservices.
     */
    @Bean
    fun postGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            chain.filter(exchange).then(Mono.fromRunnable {
                val requestHeaders = exchange.request.headers
                val correlationId = filterUtils.getCorrelationId(requestHeaders)
                log.debug("Adding correlation id to the outbound headers. {}", correlationId)
                exchange.response.headers.add(FilterUtils.CORRELATION_ID, correlationId)
                log.debug("Completing outgoing request for {}.", exchange.request.uri)
            })
        }
    }

}