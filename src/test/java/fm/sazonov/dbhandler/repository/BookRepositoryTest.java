package fm.sazonov.dbhandler.repository;

import fm.sazonov.dbhandler.TestcontainersInitializer;
import fm.sazonov.dbhandler.entity.Book;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@EnabledIf(expression = "${keysetpagination.testcontainers.enabled}", loadContext = true)
@Rollback(value = false)
@Transactional(propagation = Propagation.NEVER)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testAdding() {
        Book save = bookRepository.save(new Book());
        String id = save.getId();
        System.out.println("save.getId() = " + id);
        Optional<Book> founded = bookRepository.findById(id);
        assertEquals(id, founded.orElseThrow().getId());
    }
}