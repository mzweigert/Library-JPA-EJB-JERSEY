package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "booksAuthors.all", query = "Select ba from BooksAuthors ba"),
})
@Table(name = "BooksAuthors")
public class BooksAuthors implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBooksAuthors;

    @ManyToOne
    @JoinColumn(name = "idAuthor")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "idBook")
    private Book book;

    public BooksAuthors() {}

    public BooksAuthors(Author author, Book book) {
        this.author = author;
        this.book = book;
    }

    public long getIdBooksAuthors() {
        return idBooksAuthors;
    }

    public void setIdBooksAuthors(long idBooksAuthors) {
        this.idBooksAuthors = idBooksAuthors;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
