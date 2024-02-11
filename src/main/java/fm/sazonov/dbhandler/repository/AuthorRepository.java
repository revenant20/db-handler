package fm.sazonov.dbhandler.repository;

import fm.sazonov.dbhandler.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, String> {
    Author findAuthorByName(String name);
}
