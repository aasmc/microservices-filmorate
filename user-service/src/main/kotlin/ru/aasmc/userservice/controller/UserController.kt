package ru.aasmc.userservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.aasmc.userservice.dto.CommonEventDto
import ru.aasmc.userservice.dto.FilmDto
import ru.aasmc.userservice.dto.UserDto
import ru.aasmc.userservice.service.EventService
import ru.aasmc.userservice.service.UserService
import javax.validation.Valid

private val log = LoggerFactory.getLogger(UserController::class.java)

@RestController
@RequestMapping("/users")
class UserController(
        private val userService: UserService,
        private val eventService: EventService
) {

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable("userId") userId: Long) {
        log.info("Received request to DELETE user with id={}", userId)
        userService.deleteUser(userId)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(@RequestBody @Valid dto: UserDto): UserDto {
        log.info("Received PUT request to update User: {}", dto)
        return userService.updateUser(dto)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid dto: UserDto): UserDto {
        log.info("Received POST request to create User: {}", dto)
        return userService.createUser(dto)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllUsers(): List<UserDto> {
        log.info("Received request to GET all users.")
        return userService.findAllUsers()
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserById(@PathVariable("userId") userId: Long): UserDto {
        log.info("Received request to GET user by ID={}", userId)
        return userService.getUserById(userId)
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    fun getFriends(@PathVariable("id") id: Long): List<UserDto> {
        log.info("Received request to GET friends of user with ID={}", id)
        return userService.getFriends(id)
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    fun getCommonFriends(@PathVariable("id") id: Long,
                         @PathVariable("otherId") otherId: Long): List<UserDto> {
        log.info(
                "Received request to GET common friends of user with ID={} and user with ID={}",
                id,
                otherId
        )
        return userService.getCommonFriends(id, otherId)
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFriend(@PathVariable("id") id: Long,
                     @PathVariable("friendId") friendId: Long) {
        log.info(
                "Received request to DELETE friend with ID={}, of user with ID={}",
                friendId,
                id
        )
        userService.removeFriend(id, friendId)
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    fun addFriend(@PathVariable("id") id: Long,
                  @PathVariable("friendId") friendId: Long) {
        log.info(
                "Received PUT request to add friend with ID={}, to user with ID={}",
                friendId,
                id
        )
        userService.addFriend(id, friendId)
    }

    @GetMapping("/exists/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun isUserExists(@PathVariable("userId") userId: Long): ResponseEntity<Boolean> {
        log.info("Received GET request to check if User with ID={} exists.", userId)
        return ResponseEntity.ok(userService.isUserExists(userId))
    }

    @GetMapping("/{id}/recommendations")
    @ResponseStatus(HttpStatus.OK)
    fun getRecommendations(@PathVariable("id") userId: Long): List<FilmDto> {
        log.info("Received request to GET recommended films for user with ID={}", userId)
        return userService.getRecommendations(userId)
    }

    @GetMapping("/{id}/feed")
    @ResponseStatus(HttpStatus.OK)
    fun getEventFeedForUser(@PathVariable("id") userId: Long): List<CommonEventDto> {
        log.info("Received request to GET event feed for user with ID={}", userId)
        return eventService.getEventsForUser(userId)
    }

}