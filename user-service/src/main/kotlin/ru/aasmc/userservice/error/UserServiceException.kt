package ru.aasmc.userservice.error

import java.lang.RuntimeException

class UserServiceException(
        val code: Int,
        message: String
) : RuntimeException(message)