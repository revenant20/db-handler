package fm.sazonov.dbhandler.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "book_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
