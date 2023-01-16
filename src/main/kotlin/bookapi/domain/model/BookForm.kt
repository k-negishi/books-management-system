package bookapi.domain.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * リクエストボディでtitleとauthorのみを受け取るためのクラス
 */
data class BookForm(
    /**
     * 書籍名
     */
    @field: NotBlank
    @field: Size(max = 10)
    val title: String,

    /**
     * 著者名
     */
    @NotBlank
    @Size(max = 255)
    val author: String
)
