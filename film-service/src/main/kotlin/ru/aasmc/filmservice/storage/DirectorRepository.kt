package ru.aasmc.filmservice.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.aasmc.filmservice.model.Director

interface DirectorRepository: JpaRepository<Director, Long> {
    @Modifying
    @Query("update Director set name = :newName where id = :id")
    fun updateDirectorById(@Param("id") id: Long, @Param("newName") newName: String)

    fun findAllByIdIn(directorIds: List<Long>): MutableList<Director>

    @Query("delete from Director d where d.id =:id")
    @Modifying
    fun deleteDirectorById(@Param("id") id: Long): Int
}