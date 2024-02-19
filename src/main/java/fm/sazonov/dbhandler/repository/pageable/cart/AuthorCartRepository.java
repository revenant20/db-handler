package fm.sazonov.dbhandler.repository.pageable.cart;

import fm.sazonov.dbhandler.entity.pageable.cart.AuthorCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCartRepository extends JpaRepository<AuthorCart, String> {
    Page<AuthorCart> findAll(Pageable pageable);
}
