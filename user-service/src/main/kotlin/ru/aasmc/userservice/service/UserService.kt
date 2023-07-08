package ru.aasmc.userservice.service

import ru.aasmc.userservice.dto.FilmDto
import ru.aasmc.userservice.dto.UserDto

interface UserService {

    fun removeFriend(userId: Long, friendId: Long)

    fun addFriend(userId: Long, friendId: Long)

    fun getCommonFriends(userId: Long, friendId: Long): List<UserDto>

    fun getFriends(userId: Long): List<UserDto>

    fun getUserById(userId: Long): UserDto

    fun findAllUsers(): List<UserDto>

    fun createUser(dto: UserDto): UserDto

    fun updateUser(dto: UserDto): UserDto

    fun deleteUser(userId: Long)

    fun isUserExists(userId: Long): Boolean

    fun getRecommendations(userId: Long): List<FilmDto>

}