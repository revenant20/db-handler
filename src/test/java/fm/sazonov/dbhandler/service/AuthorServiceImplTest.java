package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.TestcontainersInitializer;
import fm.sazonov.dbhandler.entity.Author;
import fm.sazonov.dbhandler.entity.Book;
import fm.sazonov.dbhandler.repository.AuthorRepository;
import fm.sazonov.dbhandler.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@EnabledIf(expression = "${keysetpagination.testcontainers.enabled}", loadContext = true)
@Rollback(value = false)
@Transactional(propagation = Propagation.NEVER)
class AuthorServiceImplTest {

    @Autowired
    AuthorServiceImpl authorService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void testLoad() {
        System.out.println("Загрузка книг и автора");
        System.out.println();

        authorService.load();

        System.out.println();
        System.out.println("Получение автора с книгами");
        System.out.println();

        var tolkien = authorService.getAuthor("Tolkien");
        assertNotNull(tolkien);

        var books = tolkien.books();
        assertFalse(books.isEmpty());

        System.out.println();
        System.out.println("Получение книг для проверки");
        System.out.println();

        for (var book : books) {
            var bookId = book.id();
            var foundedBookOpt = bookRepository.findById(bookId);
            var foundBook = foundedBookOpt.orElseThrow();
            var author = foundBook.getAuthor();
            assertNotNull(author);
            assertNotNull(author.getId());
            assertEquals(tolkien.id(), author.getId());
        }
    }

    @Test
    void testJoinFetch() {
        System.out.println("Загрузка книг и автора");
        System.out.println();

        authorService.load();

        System.out.println();
        System.out.println("Получение автора с книгами");
        System.out.println();

        final List<Author> authors = authorRepository.findAllFetch(
               Pageable.ofSize(3).withPage(0));

        for (Author author : authors) {
            List<Book> books = author.getBooks();
            System.out.println(books.size());
        }
    }

    @Test
    void testNativePaging() {

        System.out.println("Загрузка книг и автора");
        System.out.println();

        authorService.load();

        System.out.println();
        System.out.println("Получение авторов с книгами");
        System.out.println();

        final String[] authorsWithBooks = authorRepository.findAllAuthorsNative(2, 1);
    }

}