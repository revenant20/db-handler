package fm.sazonov.dbhandler.dto;

import lombok.Builder;

@Builder
public record TagDto(String id, String name, String authorId) {
}
