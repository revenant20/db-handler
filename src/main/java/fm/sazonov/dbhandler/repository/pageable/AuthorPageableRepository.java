package fm.sazonov.dbhandler.repository.pageable;

import fm.sazonov.dbhandler.entity.pageable.AuthorPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorPageableRepository extends JpaRepository<AuthorPageable, String> {
    Page<AuthorPageable> findAll(Pageable pageable);

}
