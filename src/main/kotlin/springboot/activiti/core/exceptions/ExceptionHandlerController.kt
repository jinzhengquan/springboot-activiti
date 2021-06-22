package springboot.activiti.core.exceptions

import com.example.demo.domain.exceptions.DomainException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class ExceptionHandlerController(private var messageSource: MessageSource?) {

    @ExceptionHandler(DomainException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun domainExceptionHandler(exception: DomainException, request: WebRequest): ResponseEntity<ErrorResponseBody?>? {
        val errorResponseBody = ErrorResponseBody(
            exception.errorCode,
            getMessage(exception, request.locale)!!
        )
        return ResponseEntity.badRequest().body(errorResponseBody)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidExceptionHandler(
        exception: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponseBody?>? {
        val locale = request.locale
        val fieldError = exception.bindingResult.fieldError ?: return buildUnknownErrorResponse(locale)
        val errorCode = fieldError.defaultMessage
        if (errorCode == null || errorCode.isEmpty()) return buildUnknownErrorResponse(locale)
        val rejectedValue = fieldError.rejectedValue
        val errorMessage = messageSource!!.getMessage(errorCode, arrayOf(rejectedValue), locale)
        val errorResponseBody = ErrorResponseBody(errorCode, errorMessage)
        return ResponseEntity.badRequest().body(errorResponseBody)
    }

    fun buildUnknownErrorResponse(locale: Locale): ResponseEntity<ErrorResponseBody?>? {
        return ResponseEntity.badRequest()
            .body(ErrorResponseBody(ErrorCodes.UNKNOWN, messageSource!!.getMessage(ErrorCodes.UNKNOWN, null, locale)))
    }

    fun getMessage(exception: DomainException, locale: Locale): String? {
        return messageSource!!.getMessage(exception.errorCode, arrayOf(exception.errorMessages), locale)
    }
}

