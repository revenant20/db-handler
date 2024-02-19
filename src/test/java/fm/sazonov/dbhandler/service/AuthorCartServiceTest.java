package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.TestcontainersInitializer;
import fm.sazonov.dbhandler.repository.pageable.cart.AuthorCartRepository;
import fm.sazonov.dbhandler.repository.pageable.cart.BookCartRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@EnabledIf(expression = "${keysetpagination.testcontainers.enabled}", loadContext = true)
@Rollback(value = false)
class AuthorCartServiceTest {

    @Autowired
    AuthorCartRepository authorCartRepository;

    @Autowired
    BookCartRepository bookCartRepository;

    @Autowired
    AuthorCartService authorCartService;

    @Test
    void test() {

        System.out.println("Загрузка книг и автора");
        System.out.println();

        authorCartService.load();

        System.out.println();
        System.out.println("Получение авторов с книгами");
        System.out.println();

        var authors = authorCartService.getAuthors(3, 1);

        assertNotNull(authors);
        assertEquals(3, authors.size());

    }
}