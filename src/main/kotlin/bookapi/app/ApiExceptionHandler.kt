package bookapi.app

import bookapi.exceptions.AuthorNotFoundException
import bookapi.exceptions.DuplicationTitleAndAuthorException
import bookapi.exceptions.ErrorResponse
import bookapi.exceptions.IdNotFoundException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.ResourceBundle

/**
 * 例外をハンドリングするためのクラス
 */
@RestControllerAdvice
class ApiExceptionHandler: ResponseEntityExceptionHandler(){
    val logger: Logger = LogManager.getLogger(ApiExceptionHandler::class.java)
    // propertiesからエラーメッセージの読み込み
    val resourceBundle: ResourceBundle = ResourceBundle.getBundle("messages")

    @ExceptionHandler(AuthorNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAuthorNotFoundException(exception: AuthorNotFoundException): ErrorResponse {
        val message = resourceBundle.getString("error.AuthorNotFoundException.message")
        logger.warn(message)
        return ErrorResponse(message)
    }

    @ExceptionHandler(DuplicationTitleAndAuthorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDuplicationTitleAndAuthorException(exception: DuplicationTitleAndAuthorException): ErrorResponse {
        val message = resourceBundle.getString("error.DuplicationTitleAndAuthorException.message")
        logger.warn(message)
        return ErrorResponse(message)
    }

    @ExceptionHandler(IdNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleIdNotFoundException(exception: IdNotFoundException): ErrorResponse {
        val message = resourceBundle.getString("error.IdNotFoundException.message")
        logger.warn(message)
        return ErrorResponse(message)
    }

    /**
     * 独自例外およびSpringがハンドリングする例外以外をハンドリングする
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Override
    fun handleAll(exception: Exception): ErrorResponse {
        val message = resourceBundle.getString("error.OtherException.message")
        logger.error(message, exception)
        return ErrorResponse(message)
    }
}