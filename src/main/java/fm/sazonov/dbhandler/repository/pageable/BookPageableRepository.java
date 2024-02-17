package fm.sazonov.dbhandler.repository.pageable;

import fm.sazonov.dbhandler.entity.pageable.BookPageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPageableRepository extends CrudRepository<BookPageable, String> {
}
