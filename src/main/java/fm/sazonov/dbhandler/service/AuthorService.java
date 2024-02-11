package fm.sazonov.dbhandler.service;

import fm.sazonov.dbhandler.dto.AuthorDto;

public interface AuthorService {

    void load();

    AuthorDto getAuthor(String authorName);

}
