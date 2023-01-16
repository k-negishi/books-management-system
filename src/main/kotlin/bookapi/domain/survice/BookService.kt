package bookapi.domain.survice

import bookapi.domain.model.TitlesDto
import bookapi.domain.model.BookEntity
import bookapi.domain.model.BookForm

interface BookService {
    fun findAll(): List<BookEntity>
    fun findById(id: Long): BookEntity
    fun findByAuthor(author: String): TitlesDto
    fun search(title: String?, author: String?): List<BookEntity>
    fun create(title: BookForm): BookEntity
    fun update(bookEntity: BookEntity): BookEntity
}
