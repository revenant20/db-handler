package fm.sazonov.dbhandler.repository;

import fm.sazonov.dbhandler.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Author findAuthorByName(String name);

    @Query("""
      select a
      from Author a
      join fetch a.books
    """)
    List<Author> findAllFetch(
            Pageable pageable);

    @EntityGraph(
       attributePaths = {"books"})
    @Query("""
      select a
      from Author a
    """)
    List<Author> findAllGraph(
            Pageable pageable);

    @Query("""
      select a
      from Author a
    """)
    List<Author> findAllBasic(
            Pageable pageable);

    @Query(value = """
      select 
        a.author_id,
        a.author_name,
        b.book_id,
        b.book_name
      from authors a
          left join books b 
            on b.author_id = a.author_id
      where
        a.author_id in (
           select
             a1.author_id
           from
             authors a1
           order by a1.author_id
           limit :limit
           offset :offset
        )
    """, nativeQuery = true)
    String[] findAllAuthorsNative(
            int limit, int offset);
}
