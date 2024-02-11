package fm.sazonov.dbhandler.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "authors")
public class Author {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "author_id")
    private String id;

    @Column(name = "author_name")
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "author"
    )
    @ToString.Exclude
    private List<Book> books;
}
