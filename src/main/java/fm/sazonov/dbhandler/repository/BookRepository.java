package fm.sazonov.dbhandler.repository;

import fm.sazonov.dbhandler.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
