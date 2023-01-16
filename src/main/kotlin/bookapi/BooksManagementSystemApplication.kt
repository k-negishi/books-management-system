package bookapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooksManagementSystemApplication

fun main(args: Array<String>) {
    runApplication<BooksManagementSystemApplication>(*args)
}
