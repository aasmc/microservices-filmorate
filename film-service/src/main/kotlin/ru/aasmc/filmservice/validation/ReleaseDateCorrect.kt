package ru.aasmc.filmservice.validation

import java.time.LocalDate
import java.time.Month
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ReleaseDateValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReleaseDateCorrect(
    val message: String = "Invalid release date.",
    val groups: Array<KClass<*>> = [],
    val payLoad: Array<KClass<out Payload>> = []
)

private val THRESHOLD = LocalDate.of(1895, Month.DECEMBER, 28)

class ReleaseDateValidator: ConstraintValidator<ReleaseDateCorrect, LocalDate> {
    override fun isValid(value: LocalDate, context: ConstraintValidatorContext?): Boolean {
        return value.isAfter(THRESHOLD)
    }
}
