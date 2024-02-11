package fm.sazonov.dbhandler.repository.join.fetch;

import fm.sazonov.dbhandler.entity.join.fetch.AuthorJoinFetch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorJoinFetchRepository extends CrudRepository<AuthorJoinFetch, String> {
    @Query("select a from AuthorJoinFetch a join fetch a.books where a.name = :authorName")
    AuthorJoinFetch findAuthorByName(@Param("authorName") String authorName);
}
