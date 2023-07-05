package ru.aasmc.filmservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "urls")
class ClientProps @ConstructorBinding constructor(
        var userService: String
)