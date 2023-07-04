package ru.aasmc.gatewayserver

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.UUID

private val log = LoggerFactory.getLogger(TrackingFilter::class.java)

@Order(1)
@Component
class TrackingFilter(
        private val filterUtils: FilterUtils
): GlobalFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val requestHeaders = exchange.request.headers
        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("${FilterUtils.CORRELATION_ID} found in tracking filter: {}",
                    filterUtils.getCorrelationId(requestHeaders))
            return chain.filter(exchange)
        } else {
            val correlationId = generateCorrelationId()
            val ex = filterUtils.setCorrelationId(exchange, correlationId)
            log.debug("${FilterUtils.CORRELATION_ID} generated in tracking filter: {}",
                    correlationId)
            return chain.filter(ex)
        }
    }

    private fun isCorrelationIdPresent(requestHeaders: HttpHeaders): Boolean =
            filterUtils.getCorrelationId(requestHeaders) != null

    private fun generateCorrelationId(): String =
            UUID.randomUUID().toString()
}