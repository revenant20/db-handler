package fm.sazonov.dbhandler.dto;

import lombok.Builder;

@Builder
public record BookDto (String id, String name, String authorId){
}
