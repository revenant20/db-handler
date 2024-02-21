package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.dto.AuthorDto;
import fm.sazonov.dbhandler.dto.BookDto;
import fm.sazonov.dbhandler.dto.TagDto;
import fm.sazonov.dbhandler.entity.pageable.cart.AuthorCart;
import fm.sazonov.dbhandler.entity.pageable.cart.BookCart;
import fm.sazonov.dbhandler.entity.pageable.cart.Tag;
import fm.sazonov.dbhandler.repository.pageable.cart.AuthorCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthorCartService implements AuthorService {

    private final AuthorCartRepository authorRepository;

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
                        Sort.DEFAULT_DIRECTION, "id"
                )
        );
        return authorsPage.get()
                .map(foundedAuthor -> {
                    var books = foundedAuthor.getBooks().stream()
                            .map(it ->
                                    BookDto.builder()
                                            .id(it.getId())
                                            .name(it.getName())
                                            .authorId(it.getAuthor().getId())
                                            .build()
                            )
                            .toList();
                    var tags = foundedAuthor.getTags().stream()
                            .map(it ->
                                    TagDto.builder()
                                            .id(it.getId())
                                            .name(it.getName())
                                            .authorId(it.getAuthor().getId())
                                            .build()
                            )
                            .toList();
                    return AuthorDto.builder()
                            .id(foundedAuthor.getId())
                            .name(foundedAuthor.getName())
                            .books(books)
                            .tags(tags)
                            .build();
                }).toList();
    }

    private AuthorCart createAuthor(int num) {
        var id = num + "-" + UUID.randomUUID().toString().substring(0, 5);
        var newAuthor = new AuthorCart();
        newAuthor.setName("Tolkien-" + id);

        var silmarillion = new BookCart();
        silmarillion.setAuthor(newAuthor);
        silmarillion.setName("The Silmarillion-" + id);

        var lordsOfTheRings = new BookCart();
        lordsOfTheRings.setAuthor(newAuthor);
        lordsOfTheRings.setName("The Lords Of The Rings-" + id);

        var fantasy = new Tag();
        fantasy.setAuthor(newAuthor);
        fantasy.setName("fantasy");

        var elf = new Tag();
        elf.setAuthor(newAuthor);
        elf.setName("elf");

        newAuthor.setBooks(Set.of(silmarillion, lordsOfTheRings));
        newAuthor.setTags(Set.of(fantasy, elf));
        return newAuthor;
    }
}
