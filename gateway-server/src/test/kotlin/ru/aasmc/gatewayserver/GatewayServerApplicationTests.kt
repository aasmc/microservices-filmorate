package ru.aasmc.gatewayserver

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

const val REDIS_PORT = 6379

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class GatewayServerApplicationTests {

    companion object {
        @JvmStatic
        @Container
        val redis = GenericContainer(DockerImageName.parse("redis:7.0"))
                .withExposedPorts(REDIS_PORT)

        @JvmStatic
        @DynamicPropertySource
        fun redisProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.redis.host", redis::getHost)
            registry.add("spring.redis.port") { redis.getMappedPort(REDIS_PORT) }
        }
    }

    @Test
    fun contextLoads() {
    }

}
