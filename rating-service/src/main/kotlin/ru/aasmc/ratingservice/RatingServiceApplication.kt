package ru.aasmc.ratingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableEurekaClient
class RatingServiceApplication

fun main(args: Array<String>) {
    runApplication<RatingServiceApplication>(*args)
}
