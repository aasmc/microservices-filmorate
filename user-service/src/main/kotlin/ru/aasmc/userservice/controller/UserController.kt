package ru.aasmc.userservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.apache.kafka.common.protocol.types.Field.Bool
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
import ru.aasmc.userservice.error.ErrorResponse
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

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Successfully deleted user."
        )
    ])
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable("userId") userId: Long) {
        log.info("Received request to DELETE user with id={}", userId)
        userService.deleteUser(userId)
    }

    @Operation(summary = "Updating user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully updated user",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ]),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(@RequestBody @Valid dto: UserDto): UserDto {
        log.info("Received PUT request to update User: {}", dto)
        return userService.updateUser(dto)
    }

    @Operation(summary = "Creating user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully created user",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserDto::class)
                )]
        ),
        ApiResponse(
                responseCode = "400",
                description = "Invalid request.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid dto: UserDto): UserDto {
        log.info("Received POST request to create User: {}", dto)
        return userService.createUser(dto)
    }

    @Operation(summary = "Get a list of all users.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = UserDto::class)))
                )]
        )
    ])
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllUsers(): List<UserDto> {
        log.info("Received request to GET all users.")
        return userService.findAllUsers()
    }

    @Operation(summary = "Get user by ID.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserDto::class))
                ]),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserById(@PathVariable("userId") userId: Long): UserDto {
        log.info("Received request to GET user by ID={}", userId)
        return userService.getUserById(userId)
    }

    @Operation(summary = "Get a list of friends of the specified user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = UserDto::class)))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    fun getFriends(@PathVariable("id") id: Long): List<UserDto> {
        log.info("Received request to GET friends of user with ID={}", id)
        return userService.getFriends(id)
    }

    @Operation(summary = "Get a list of common friends of the specified user and his/her friend.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = UserDto::class)))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user / friend by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Delete a friend from the specified user's friend list.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "204",
                description = "Successfully deleted friend of user."
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user / friend by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Add a friend to the specified user's friend list.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Successfully added friend of user."
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user / friend by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
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

    @Operation(summary = "Checks if a user with specified id exists.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success.",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = Boolean::class)
                )]
        )
    ])
    @GetMapping("/exists/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun isUserExists(@PathVariable("userId") userId: Long): ResponseEntity<Boolean> {
        log.info("Received GET request to check if User with ID={} exists.", userId)
        return ResponseEntity.ok(userService.isUserExists(userId))
    }

    @Operation(summary = "Get a list of films recommended to the specified user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = FilmDto::class)))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}/recommendations")
    @ResponseStatus(HttpStatus.OK)
    fun getRecommendations(@PathVariable("id") userId: Long): List<FilmDto> {
        log.info("Received request to GET recommended films for user with ID={}", userId)
        return userService.getRecommendations(userId)
    }

    @Operation(summary = "Get a list of events performed by the specified user.")
    @ApiResponses(value = [
        ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = (ArraySchema(schema = Schema(implementation = CommonEventDto::class)))
                )]
        ),
        ApiResponse(
                responseCode = "404",
                description = "Cannot find user by ID.",
                content = [
                    Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = ErrorResponse::class))
                ])
    ])
    @GetMapping("/{id}/feed")
    @ResponseStatus(HttpStatus.OK)
    fun getEventFeedForUser(@PathVariable("id") userId: Long): List<CommonEventDto> {
        log.info("Received request to GET event feed for user with ID={}", userId)
        return eventService.getEventsForUser(userId)
    }

}