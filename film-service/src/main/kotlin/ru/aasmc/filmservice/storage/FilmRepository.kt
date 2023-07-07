package ru.aasmc.filmservice.storage

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.aasmc.filmservice.model.Film

interface FilmRepository : JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    @Query("select f from Film f order by f.rate desc")
    fun findAllSorted(): List<Film>

    @Query("select f from Film f order by f.rate desc")
    fun findAllSortedLimit(pageable: Pageable): List<Film>

    @Query("select f from Film f where year(f.releaseDate) =:year order by f.rate desc")
    fun findAllByYearOrderByRateDesc(@Param("year") year: Int, pageable: Pageable): List<Film>

    @Query("""
        SELECT f.* FROM FILMS f 
        LEFT JOIN FILM_GENRE fg ON f.id = fg.film_id 
        LEFT JOIN Rating r ON r.id = f.rating_id
        LEFT JOIN FILM_DIRECTOR fd ON f.id = fd.film_id
        LEFT JOIN DIRECTORS d ON d.id = fd.director_id
        WHERE fg.genre_id = ? 
        LIMIT ?
    """, nativeQuery = true )
    fun findAllByGenreSortedByRate(@Param("genreId") genreId: Int, count: Int): List<Film>

    @Query("""
        SELECT f.* FROM FILMS f 
        LEFT JOIN FILM_GENRE fg ON f.id = fg.film_id 
        LEFT JOIN Rating r ON r.id = f.rating_id
        LEFT JOIN FILM_DIRECTOR fd ON f.id = fd.film_id
        LEFT JOIN DIRECTORS d ON d.id = fd.director_id
        WHERE fg.genre_id = ? AND EXTRACT(YEAR FROM CAST(f.release_date AS date)) = ? 
        LIMIT ?
    """, nativeQuery = true )
    fun findAllByGenresAndYearSortedByRate(
            @Param("genreId") genreId: Int,
            @Param("year") year: Int,
            count: Int
    ): List<Film>

    @Modifying
    @Query("delete from Film f where f.id = :id")
    fun deleteFilmById(@Param("id") id: Long): Int

    fun findAllByIdIn(filmIds: List<Long>): List<Film>

    @Modifying
    @Query("update Films set rate = ? where id = ?", nativeQuery = true)
    fun setRateToFilm(rate: Double, filmId: Long)
}