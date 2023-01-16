package bookapi.domain.survice

import bookapi.domain.model.TitlesDto
import bookapi.domain.model.BookEntity
import bookapi.domain.model.BookForm
import bookapi.domain.repository.BookRepository
import bookapi.exceptions.AuthorNotFoundException
import bookapi.exceptions.DuplicationTitleAndAuthorException
import bookapi.exceptions.IdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImp(
    val bookRepository: BookRepository,
) : BookService {


    /**
     * 全書籍情報を取得する
     */
    override fun findAll(): List<BookEntity> {
        return bookRepository.findAll()
    }

    /**
     * IDに紐づく書籍情報を取得する
     */
    override fun findById(id: Long): BookEntity {
        // IDに紐づく書籍が存在しない場合、エラー
        return bookRepository.findById(id).orElseThrow { IdNotFoundException() }
    }

    /**
     * 著者を指定して書籍情報を取得する
     */
    override fun findByAuthor(author: String): TitlesDto {
        val books = bookRepository.findByAuthor(author)

        // 著者に紐づく書籍が存在しない場合
        if (books.isEmpty()) throw AuthorNotFoundException()

        return TitlesDto(books.map { it.title })
    }

    /**
     * タイトル、著者を指定して書籍情報を検索する
     */
    override fun search(title: String?, author: String?): List<BookEntity> {
        // リクエストパラメータのtitleとauthorは任意項目のため、セットされていない(nullの場合)は空文字列をセットする
        val searchTitle: String = title ?: ""
        val searchAuthor: String = author ?: ""
        return bookRepository.findByTitleContainingAndAuthorContaining(searchTitle, searchAuthor)
    }

    /**
     * 書籍を登録する
     */
    @Transactional
    override fun create(bookForm: BookForm): BookEntity {
        val title: String = bookForm.title
        val author: String = bookForm.author
        // タイトルと著者がすでに存在する場合、エラー
        if (bookRepository.existsByTitleAndAuthor(title, author)) {
            throw DuplicationTitleAndAuthorException()
        }

        // idは自動生成となるため、BookEntityの引数にダミーとしてid = 0を渡す
        val bookEntity = BookEntity(id = 0, title = title, author = author)

        return bookRepository.saveAndFlush(bookEntity)
    }

    /**
     * 書籍情報を更新する
     */
    @Transactional
    override fun update(bookEntity: BookEntity): BookEntity {
        // idが存在しない場合、エラー
        if (!bookRepository.existsById(bookEntity.id)) {
            throw IdNotFoundException()
        }

        // タイトルと著者がすでに存在する場合、エラー
        if (bookRepository.existsByTitleAndAuthor(bookEntity.title, bookEntity.author)) {
            throw DuplicationTitleAndAuthorException()
        }
        return bookRepository.saveAndFlush(bookEntity)
    }
}
