package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.TestcontainersInitializer;
import fm.sazonov.dbhandler.repository.AuthorRepository;
import fm.sazonov.dbhandler.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void testLoad() {
        authorService.load();

        var tolkien = authorService.getAuthor("Tolkien");
        assertNotNull(tolkien);

        var books = tolkien.books();
        assertFalse(books.isEmpty());

        for (var book : books) {
            var bookId = book.id();
            var foundedBookOpt = bookRepository.findById(bookId);
            var foundedBook = foundedBookOpt.orElseThrow();
            var author = foundedBook.getAuthor();
            assertNotNull(author);
            assertNotNull(author.getId());
            assertEquals(tolkien.id(), author.getId());
        }
    }
}