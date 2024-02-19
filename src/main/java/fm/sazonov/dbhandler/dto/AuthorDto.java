package fm.sazonov.dbhandler.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDto(String id, String name, List<BookDto> books, List<TagDto> tags) {
}
