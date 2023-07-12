package ru.aasmc.userservice.service.impl

import org.springframework.context.ApplicationEventPublisher
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.aasmc.userservice.appevents.EventOperation
import ru.aasmc.userservice.appevents.UserFriendEvent
import ru.aasmc.userservice.client.RecommendationsClient
import ru.aasmc.userservice.dto.FilmDto
import ru.aasmc.userservice.dto.UserDto
import ru.aasmc.userservice.error.UserServiceException
import ru.aasmc.userservice.mapper.UserMapper
import ru.aasmc.userservice.model.User
import ru.aasmc.userservice.repository.UserRepository
import ru.aasmc.userservice.service.UserService
import java.time.Instant

@Service
@Transactional
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val userMapper: UserMapper,
        private val client: RecommendationsClient
) : UserService {

    override fun removeFriend(userId: Long, friendId: Long) {
        checkUserExists(userId)
        checkUserExists(friendId)
        val deletedRows = userRepository.deleteFriend(userId, friendId)
        if (deletedRows != 0) {
            applicationEventPublisher.publishEvent(
                    UserFriendEvent(
                            source = this,
                            timeStamp = Instant.now().toEpochMilli(),
                            userId = userId,
                            operation = EventOperation.REMOVE,
                            friendId = friendId
                    )
            )
        }
    }

    override fun addFriend(userId: Long, friendId: Long) {
        try {
            userRepository.addFriend(userId, friendId)
        } catch (e: RuntimeException) {
            if (e is DataIntegrityViolationException) {
                val msg = "Either User with ID=$userId or Friend with ID=$friendId is not found in DB."
                throw UserServiceException(HttpStatus.NOT_FOUND.value(), msg)
            }
            throw e
        }
        applicationEventPublisher.publishEvent(
                UserFriendEvent(
                        source = this,
                        timeStamp = Instant.now().toEpochMilli(),
                        userId = userId,
                        friendId = friendId,
                        operation = EventOperation.ADD
                )
        )
    }

    override fun getCommonFriends(userId: Long, friendId: Long): List<UserDto> {
        val userFriends = getFriends(userId).toHashSet()
        val friendFriends = getFriends(friendId).toHashSet()
        userFriends.retainAll(friendFriends)
        return userFriends.toList()
    }

    override fun getFriends(userId: Long): List<UserDto> {
        val user = getUserByIdOrThrow(userId)
        return user.friends.map { userMapper.mapToDto(it) }
    }

    override fun getUserById(userId: Long): UserDto {
        val user = getUserByIdOrThrow(userId)
        return userMapper.mapToDto(user)
    }

    override fun findAllUsers(): List<UserDto> {
        return userRepository.findAll()
                .map { userMapper.mapToDto(it) }
    }

    override fun createUser(dto: UserDto): UserDto {
        val newUser = userMapper.mapToDomain(dto)
        val saved = userRepository.save(newUser)
        return userMapper.mapToDto(saved)
    }

    override fun updateUser(dto: UserDto): UserDto {
        val rowsUpdated = userRepository.updateUser(dto.email,
                dto.login,
                dto.name ?: dto.login,
                dto.birthday,
                dto.id ?: 0)
        if (rowsUpdated != 0) {
            return dto
        } else {
            val msg = "User with ID = ${dto.id} not found."
            throw UserServiceException(HttpStatus.NOT_FOUND.value(), msg)
        }
    }

    override fun deleteUser(userId: Long) {
        val rowsDeleted = userRepository.deleteUserWithId(userId)
        if (rowsDeleted != 0) {
            applicationEventPublisher.publishEvent(
                    UserFriendEvent(
                            source = this,
                            timeStamp = Instant.now().toEpochMilli(),
                            userId = userId,
                            operation = EventOperation.REMOVE_ALL,
                            friendId = -1
                    )
            )
        }
    }

    override fun isUserExists(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

    override fun getRecommendations(userId: Long): List<FilmDto> {
        checkUserExists(userId)
        return client.getRecommendations(userId)
    }

    private fun checkUserExists(userId: Long) {
        if (!isUserExists(userId)) {
            throw UserServiceException(HttpStatus.NOT_FOUND.value(), "User with ID=$userId not found.")
        }
    }

    private fun getUserByIdOrThrow(userId: Long): User {
        return userRepository.findById(userId)
                .orElseThrow {
                    val msg = "User with ID=$userId not found."
                    UserServiceException(HttpStatus.NOT_FOUND.value(), msg)
                }
    }
}