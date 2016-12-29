package entities;

import org.hibernate.annotations.Proxy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by ckguven on 12/29/16.
 */

@Entity
@Table(name = "pages_table")
@Audited
@Proxy(lazy = false)
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private ZonedDateTime updatedAt;

    @Column
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
