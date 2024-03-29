package fm.sazonov.dbhandler.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "authors")
public class Author {

    @Id
    @EqualsAndHashCode.Exclude
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "author_id")
    private String id;

    @Column(name = "author_name")
    private String name;

    //@BatchSize(size = 2)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "author"
    )
    @ToString.Exclude
    private List<Book> books;
}
