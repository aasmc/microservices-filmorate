package ru.aasmc.filmservice.storage

import org.springframework.data.jpa.domain.Specification
import ru.aasmc.filmservice.model.Film
import ru.aasmc.filmservice.model.SearchBy

class FilmSpecificationBuilder {
    private val params: MutableList<SearchBy> = arrayListOf()

    fun with(searchBy: SearchBy): FilmSpecificationBuilder {
        params.add(searchBy)
        return this
    }

    fun build(query: String): Specification<Film>? {
        if (params.isEmpty()) {
            return null
        }
        var result: Specification<Film> = FilmSpecification(params[0], query)
        for (i in 1 until params.size) {
            val criteria = params[i]
            result = Specification.where(result)
                .or(FilmSpecification(criteria, query))
        }
        return result
    }
}