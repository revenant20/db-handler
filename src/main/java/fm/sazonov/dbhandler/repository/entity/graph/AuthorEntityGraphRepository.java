package fm.sazonov.dbhandler.repository.entity.graph;

import fm.sazonov.dbhandler.entity.graph.AuthorEntityGraph;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface AuthorEntityGraphRepository extends CrudRepository<AuthorEntityGraph, String> {
    @EntityGraph(attributePaths = {"books"})
    AuthorEntityGraph findAuthorByName(String name);
}
