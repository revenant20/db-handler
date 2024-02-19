package fm.sazonov.dbhandler.entity.pageable.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "tags")
public class Tag {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "tag_id")
    private String id;

    @Column(name = "tag_name")
    private String name;

    @ToString.Exclude
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id")
    private AuthorCart author;
}
