package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.dto.AuthorDto;
import fm.sazonov.dbhandler.dto.BookDto;
import fm.sazonov.dbhandler.entity.pageable.AuthorPageable;
import fm.sazonov.dbhandler.entity.pageable.BookPageable;
import fm.sazonov.dbhandler.repository.pageable.AuthorPageableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthorPageableServiceImpl implements AuthorService {

    private final AuthorPageableRepository authorRepository;

    @Transactional
    @Override
    public void load() {
        authorRepository.saveAll(
                IntStream.range(0, 20)
                        .boxed()
                        .map(this::createAuthor)
                        .toList()
        );
    }

    @Transactional
    @Override
    public AuthorDto getAuthor(String authorName) {
        throw new RuntimeException("Метод не поддерживается");
    }

    @Transactional
    public List<AuthorDto> getAuthors(int limit, int offset) {
        var authorsPage = authorRepository.findAll(
                PageRequest.of(
                        offset,
                        limit,
                        Sort.DEFAULT_DIRECTION, "author_xid"
                )
        );
        return authorsPage.get()
                .map(foundedAuthor -> {
                    var books = foundedAuthor.getBooks().stream()
                            .map(ent ->
                                    BookDto.builder()
                                            .id(ent.getId())
                                            .name(ent.getName())
                                            .authorId(ent.getAuthor().getId())
                                            .build()
                            )
                            .toList();
                    return AuthorDto.builder()
                            .id(foundedAuthor.getId())
                            .name(foundedAuthor.getName())
                            .books(books)
                            .build();
                }).toList();
    }

    private AuthorPageable createAuthor(int num) {
        var id = num + "-" + UUID.randomUUID().toString().substring(0, 5);
        var newAuthor = new AuthorPageable();
        newAuthor.setName("Tolkien-" + id);

        var silmarillion = new BookPageable();
        silmarillion.setAuthor(newAuthor);
        silmarillion.setName("The Silmarillion-" + id);

        var lordsOfTheRings = new BookPageable();
        lordsOfTheRings.setAuthor(newAuthor);
        lordsOfTheRings.setName("The Lords Of The Rings-" + id);

        newAuthor.setBooks(List.of(silmarillion, lordsOfTheRings));
        return newAuthor;
    }
}
