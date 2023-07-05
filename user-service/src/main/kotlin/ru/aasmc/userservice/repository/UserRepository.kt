package ru.aasmc.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.aasmc.userservice.model.User
import java.time.LocalDate

interface UserRepository: JpaRepository<User, Long> {

    @Modifying
    @Query("DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?", nativeQuery = true)
    fun deleteFriend(@Param("userId") userId: Long, @Param("friendId") friendId: Long): Int

    @Modifying
    @Query("INSERT INTO FRIENDS(USER_ID, FRIEND_ID) VALUES(?,?)", nativeQuery = true)
    fun addFriend(@Param("userId") userId: Long, @Param("friendId") friendId: Long)

    @Modifying
    @Query("UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?", nativeQuery = true)
    fun updateUser(email: String, login: String, name: String, bd: LocalDate, userId: Long): Int

    @Modifying
    @Query("DELETE FROM USERS WHERE USER_ID = ?", nativeQuery = true)
    fun deleteUserWithId(id: Long): Int

}