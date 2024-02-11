package fm.sazonov.dbhandler.repository.join.fetch;

import fm.sazonov.dbhandler.entity.join.fetch.BookJoinFetch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJoinFetchRepository extends CrudRepository<BookJoinFetch, String> {
}
