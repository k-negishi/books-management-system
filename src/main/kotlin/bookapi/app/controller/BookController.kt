package bookapi.app.controller

import bookapi.domain.model.TitlesDto
import bookapi.domain.model.BookEntity
import bookapi.domain.model.BookForm
import bookapi.domain.survice.BookService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/book")
class BookController(val bookService: BookService) {

    val logger: Logger = LogManager.getLogger(BookController::class.java)

    @GetMapping
    fun findAll(): List<BookEntity> {
        logger.info("findAll is called")
        return bookService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): BookEntity {
        return bookService.findById(id)
    }

    @GetMapping("/search")
    fun search(
        @RequestParam(name = "title", required = false) title: String?,
        @RequestParam(name = "author", required = false) author: String?
    ): List<BookEntity> {
        logger.info("search is called with title($title) and author($author)")
        return bookService.search(title, author)
    }

    @GetMapping("/author/{author}")
    fun findByAuthor(@PathVariable author: String): TitlesDto {
        logger.info("findByAuthor is called with author($author)")
        return bookService.findByAuthor(author)

    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Validated @RequestBody bookForm: BookForm
    ): BookEntity {
        logger.info("create is called with bookForm($bookForm)")
        return bookService.create(bookForm)
    }

    @PutMapping("/update")
    fun update(@Validated @RequestBody bookEntity: BookEntity): BookEntity {
        logger.info("update is called with bookForm($bookEntity)")
        return bookService.update(bookEntity)
    }
}
