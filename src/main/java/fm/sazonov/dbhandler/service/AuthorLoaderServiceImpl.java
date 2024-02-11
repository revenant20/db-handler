package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.entity.Author;
import fm.sazonov.dbhandler.entity.Book;
import fm.sazonov.dbhandler.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorLoaderServiceImpl implements AuthorLoaderService {

    private final AuthorRepository authorRepository;

    @Override
    public void load() {
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
