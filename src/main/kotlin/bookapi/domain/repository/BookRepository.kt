package bookapi.domain.repository

import bookapi.domain.model.BookEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<BookEntity, Long> {
    fun existsByTitleAndAuthor(title: String, author: String): Boolean

    fun findByTitleContainingAndAuthorContaining(title: String, author: String): List<BookEntity>

    fun findByAuthor(author: String): List<BookEntity>

}
