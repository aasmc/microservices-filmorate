package ru.aasmc.gatewayserver

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class FilterUtils {
    companion object {
        const val CORRELATION_ID = "X-Correlation-Id"
    }

    fun getCorrelationId(requestHeaders: HttpHeaders): String? {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            val header = requestHeaders.get(CORRELATION_ID)
            return header?.first()
        }
        return null
    }

    fun setRequestHeader(exchange: ServerWebExchange,
                         name: String,
                         value: String): ServerWebExchange {
        return exchange.mutate().request(
                exchange.request.mutate()
                        .header(name, value)
                        .build()
        ).build()
    }

    fun setCorrelationId(exchange: ServerWebExchange, correlationId: String): ServerWebExchange {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId)
    }
}