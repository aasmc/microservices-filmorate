package ru.aasmc.filmservice.storage

import org.springframework.data.jpa.domain.Specification
import ru.aasmc.filmservice.model.Director
import ru.aasmc.filmservice.model.Film
import ru.aasmc.filmservice.model.SearchBy
import javax.persistence.criteria.*

class FilmSpecification(
    private val searchBy: SearchBy,
    private val queryStr: String
) : Specification<Film> {
    override fun toPredicate(
        root: Root<Film>,
        query: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate? {
        return when (searchBy) {
            SearchBy.title -> {
                cb.like(
                    cb.lower(
                        root
                            .get(searchBy.columName)
                    ),
                    "%$queryStr%"
                )
            }

            SearchBy.director -> {
                cb.like(
                    cb.lower(
                        joinDirectors(root)
                            .get(searchBy.columName)
                    ),
                    "%$queryStr%"
                )
            }
        }
    }

    private fun joinDirectors(root: Root<Film>): Join<Film, Director> {
        return root.joinList("directors", JoinType.LEFT)
    }
}