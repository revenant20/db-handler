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

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@EnabledIf(expression = "${keysetpagination.testcontainers.enabled}", loadContext = true)
@Rollback(value = false)
@Transactional(propagation = Propagation.NEVER)
class AuthorServiceNPlusOneTest {

    @Autowired
    AuthorServiceImpl authorService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @PostConstruct
    public void setUp() {
        authorService.load();

        {
            var newAuthor = new Author();

            newAuthor.setName("Rowling");

            var hp = new Book();
            hp.setAuthor(newAuthor);
            hp.setName("Harry Potter");

            newAuthor.setBooks(List.of(hp));

            authorRepository.save(newAuthor);
        }
        {
            var newAuthor = new Author();

            newAuthor.setName("Martin");

            var hp = new Book();
            hp.setAuthor(newAuthor);
            hp.setName("Clean Code");

            newAuthor.setBooks(List.of(hp));

            authorRepository.save(newAuthor);
        }
    }

    @Test
    @Transactional
    void testNplusOne() {

        List<Author> authors = authorRepository.findAll();

        for (Author author : authors) {
            List<Book> books = author.getBooks();
            System.out.println(books.size());
        }

    }

    @Test
    @Transactional
    void testNplusOnePageable() {

        List<Author> authors = authorRepository.findAllBasic(
                Pageable.ofSize(10).withPage(0));

        for (Author author : authors) {
            List<Book> books = author.getBooks();
            System.out.println(books.size());
        }

    }

    @Test
    void testNplusOneNative() {

        String[] authors = authorRepository.findAllAuthorsNative(
                1, 0);

    }

    @Test
    void testGraph() {

        List<Author> authors = authorRepository.findAllGraph(
                Pageable.ofSize(1).withPage(0));

        for (Author author : authors) {
            List<Book> books = author.getBooks();
            System.out.println(books.size());
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

        List<Author> authors = authorRepository.findAllFetch(
                Pageable.ofSize(3).withPage(0));

        for (Author author : authors) {
            List<Book> books = author.getBooks();
            System.out.println(books.size());
        }
    }
}