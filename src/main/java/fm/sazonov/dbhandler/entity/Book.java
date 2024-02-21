package fm.sazonov.dbhandler.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "books")
public class Book {

    @Id
    @EqualsAndHashCode.Include
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "book_id")
    private String id;

    @Column(name = "book_name")
    private String name;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @JoinColumn(name = "author_id")
    private Author author;
}
