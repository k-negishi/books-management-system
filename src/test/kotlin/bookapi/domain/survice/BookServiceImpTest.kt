import bookapi.domain.model.TitlesDto
import bookapi.domain.model.BookEntity
import bookapi.domain.model.BookForm
import bookapi.domain.repository.BookRepository
import bookapi.domain.survice.BookServiceImp
import bookapi.exceptions.AuthorNotFoundException
import bookapi.exceptions.DuplicationTitleAndAuthorException
import bookapi.exceptions.IdNotFoundException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
class BookServiceImpTest {
    @InjectMocks
    lateinit var bookService: BookServiceImp
    @Mock
    lateinit var bookRepository: BookRepository

    private val testBook1 = BookEntity(id = 1, title = "test title1", author = "test author1")
    private val testBook2 = BookEntity(id = 2, title = "test title2", author = "test author2")
    private val testBook3 = BookEntity(id = 3, title = "test title2", author = "test author3")
    private val testBooks = listOf(testBook1, testBook2, testBook3)
    private val testBookForm = BookForm(title = "test title1", author = "test author1")
    private val testTitlesDto = TitlesDto(listOf("test title1"))

    @Test
    fun `書籍情報全件取得_正常系`() {
        Mockito.`when`(bookRepository.findAll()).thenReturn(testBooks)
        val actual = bookService.findAll()
        Assert.assertEquals(testBooks, actual)
    }

    @Test
    fun `idから書籍情報1件取得_正常系`() {
        Mockito.`when`(bookRepository.findById(1)).thenReturn(Optional.of(testBook1))
        val actual = bookService.findById(1)
        Assert.assertEquals(testBook1, actual)
    }

    @Test(expected = IdNotFoundException::class)
    fun `idから書籍1件取得_異常系_IDが存在しない場合`() {
        Mockito.`when`(bookRepository.findById(1)).thenReturn(Optional.empty())
        bookService.findById(1)
    }

    @Test
    fun `著者名指定書籍取得_正常系`() {
        Mockito.`when`(bookRepository.findByAuthor("test author1")).thenReturn(listOf(testBook1))
        val actual = bookService.findByAuthor("test author1")
        Assert.assertEquals(testTitlesDto, actual)
    }

    @Test(expected = AuthorNotFoundException::class)
    fun `著者名指定書籍取得_異常系_著者が存在しない場合`() {
        Mockito.`when`(bookRepository.findByAuthor("test author1")).thenReturn(emptyList())
        bookService.findByAuthor("test author1")
    }

    @Test
    fun `書籍名または著者名指定書籍検索_正常系`() {
        Mockito.`when`(bookRepository.findByTitleContainingAndAuthorContaining("test title", "test author"))
            .thenReturn(testBooks)
        val actual = bookService.search("test title", "test author")
        Assert.assertEquals(testBooks, actual)
    }

    @Test
    fun `書籍情報登録_正常系`() {
        val createdBookEntity = BookEntity (id = 0, title = "test title1", author = "test author1")
        Mockito.`when`(bookRepository.existsByTitleAndAuthor("test title1", "test author1")).thenReturn(false)
        Mockito.`when`(bookRepository.saveAndFlush(createdBookEntity)).thenReturn(testBook1)
        val actual = bookService.create(testBookForm)
        Assert.assertEquals(testBook1, actual)
    }

    @Test(expected = DuplicationTitleAndAuthorException::class)
    fun `書籍情報登録_異常系_書籍名および著者名が重複している場合`() {
        Mockito.`when`(bookRepository.existsByTitleAndAuthor("test title1", "test author1")).thenReturn(true)
        bookService.create(testBookForm)
    }

    @Test
    fun `書籍情報更新_正常系`() {
        Mockito.`when`(bookRepository.existsById(1)).thenReturn(true)
        Mockito.`when`(bookRepository.existsByTitleAndAuthor("test title1", "test author1")).thenReturn(false)
        Mockito.`when`(bookRepository.saveAndFlush(testBook1)).thenReturn(testBook1)
        val actual = bookService.update(testBook1)
        Assert.assertEquals(testBook1, actual)
    }

    @Test(expected = IdNotFoundException::class)
    fun `書籍情報更新_異常系_IDが存在しない場合`() {
        Mockito.`when`(bookRepository.existsById(1)).thenReturn(false)
        bookService.update(testBook1)
    }

    @Test(expected = DuplicationTitleAndAuthorException::class)
    fun `書籍情報更新_異常系_書籍名および著者が重複している場合`() {
        Mockito.`when`(bookRepository.existsById(1)).thenReturn(true)
        Mockito.`when`(bookRepository.existsByTitleAndAuthor("test title1", "test author1")).thenReturn(true)
        bookService.update(testBook1)
    }
}


