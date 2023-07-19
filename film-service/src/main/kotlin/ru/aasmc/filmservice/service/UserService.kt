package ru.aasmc.filmservice.service

interface UserService {
    fun isUserExists(userId: Long): Boolean
}