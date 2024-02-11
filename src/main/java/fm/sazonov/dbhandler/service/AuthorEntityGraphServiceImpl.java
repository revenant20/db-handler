package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.dto.AuthorDto;
import fm.sazonov.dbhandler.dto.BookDto;
import fm.sazonov.dbhandler.entity.graph.AuthorEntityGraph;
import fm.sazonov.dbhandler.entity.graph.BookEntityGraph;
import fm.sazonov.dbhandler.repository.entity.graph.AuthorEntityGraphRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorEntityGraphServiceImpl implements AuthorService {

    private final AuthorEntityGraphRepository authorRepository;

    @Transactional
    @Override
    public void load() {
        var newAuthor = new AuthorEntityGraph();
        newAuthor.setName("Tolkien");

        var silmarillion = new BookEntityGraph();
        silmarillion.setAuthor(newAuthor);
        silmarillion.setName("The Silmarillion");

        var lordsOfTheRings = new BookEntityGraph();
        lordsOfTheRings.setAuthor(newAuthor);
        lordsOfTheRings.setName("The Lords Of The Rings");

        newAuthor.setBooks(List.of(silmarillion, lordsOfTheRings));
        authorRepository.save(newAuthor);
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
