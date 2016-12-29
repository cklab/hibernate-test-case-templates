package entities;

import javax.persistence.*;

/**
 * Created by ckguven on 12/29/16.
 */

@Embeddable
public class PageReview {

    @Column
    private String comments;

    @ManyToOne(optional = true)
    @JoinColumn(name="page_id", nullable = true)
    private Page page;


    public PageReview() {
    }

    public PageReview(String comments) {
        this.comments = comments;
    }

    public PageReview(String comments, Page page) {
        this.comments = comments;
        this.page = page;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
