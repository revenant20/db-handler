package fm.sazonov.dbhandler.repository.pageable.cart;

import fm.sazonov.dbhandler.entity.pageable.cart.BookCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCartRepository extends CrudRepository<BookCart, String> {
}
