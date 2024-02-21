package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.dto.AuthorDto;
import fm.sazonov.dbhandler.dto.BookDto;
import fm.sazonov.dbhandler.entity.Author;
import fm.sazonov.dbhandler.entity.Book;
import fm.sazonov.dbhandler.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void load() {
        {
            var newAuthor = new Author();
            newAuthor.setName("Tolkien");

            var silmarillion = new Book();
            silmarillion.setAuthor(newAuthor);
            silmarillion.setName("The Silmarillion");

            var lordsOfTheRings = new Book();
            lordsOfTheRings.setAuthor(newAuthor);
            lordsOfTheRings.setName("The Lords Of The Rings");

            newAuthor.setBooks(List.of(silmarillion, lordsOfTheRings));
            authorRepository.save(newAuthor);
        }
    }

    @Transactional
    @Override
    public AuthorDto getAuthor(String authorName) {
        var foundedAuthor = authorRepository.findAuthorByName(authorName);
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
    }
}
