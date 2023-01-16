package bookapi.domain.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "book")
data class BookEntity(
    /**
     * id(自動生成)
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "book_id_generator"
    )
    @SequenceGenerator(
        name = "book_id_generator",
        sequenceName = "book_id_sequence",
        allocationSize = 1
    )
    val id: Long,

    /**
     * 書籍名
     */
    @NotBlank
    @Size(max = 255)
    val title: String,

    /**
     * 著者名
     */
    @NotBlank
    @Size(max = 255)
    val author: String
)
