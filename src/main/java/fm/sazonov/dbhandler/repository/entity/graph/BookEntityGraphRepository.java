package fm.sazonov.dbhandler.repository.entity.graph;

import fm.sazonov.dbhandler.entity.graph.BookEntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityGraphRepository extends CrudRepository<BookEntityGraph, String> {
}
