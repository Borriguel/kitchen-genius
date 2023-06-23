package dev.borriguel.kitchengenius.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.Instant

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun resourceNotFoundException(exception: ResourceNotFoundException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Instant.now(),
            exception.message,
            request.getDescription(false)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun validationException(exception: MethodArgumentNotValidException, request: WebRequest): ValidationErrorMessage {
        val listErrorMessage = mutableListOf<String>()
        for (f in exception.bindingResult.fieldErrors) listErrorMessage.add("${f.defaultMessage}")
        return ValidationErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            Instant.now(),
            listErrorMessage,
            request.getDescription(false)
        )
    }
}