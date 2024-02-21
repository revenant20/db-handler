package fm.sazonov.dbhandler.repository.pageable.cart;

import fm.sazonov.dbhandler.entity.pageable.cart.AuthorCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorCartRepository extends JpaRepository<AuthorCart, String> {
    Page<AuthorCart> findAll(Pageable pageable);

    @Query(""
            + "select a "
            + "from AuthorCart a "
            + "left join fetch a.tags "
            + "left join fetch a.books "
    )
    Slice<AuthorCart> findAllAuthors(Pageable pageable);
}
