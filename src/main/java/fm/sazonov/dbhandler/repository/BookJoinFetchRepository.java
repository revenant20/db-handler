package fm.sazonov.dbhandler.repository;

import fm.sazonov.dbhandler.entity.BookJoinFetch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJoinFetchRepository extends CrudRepository<BookJoinFetch, String> {
}
