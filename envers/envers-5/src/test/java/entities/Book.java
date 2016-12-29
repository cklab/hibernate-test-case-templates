package entities;



import org.hibernate.annotations.Proxy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name="books")
@Audited
@Proxy(lazy = false)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ElementCollection
    @CollectionTable(
            name = "page_reviews",
            joinColumns= @JoinColumn(name = "book_id")
    )
    @OrderColumn(name = "page_order")
    private List<PageReview> tornPages;

    @Column
    private ZonedDateTime updatedAt;

    public List<PageReview> getTornPages() {
        return tornPages;
    }

    public void setTornPages(List<PageReview> tornPages) {
        this.tornPages = tornPages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
