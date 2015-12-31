package com.library.service;


import com.library.BookDAO;
import com.library.domain.Author;
import com.library.domain.Book;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookManager implements BookDAO
{

    @PersistenceContext
    EntityManager em;

    public List<Book> getAllBooks()
    {
        return em.createNamedQuery("book.all").getResultList();
    }

    public Book getBookById(Book book)
    {
        return em.find(Book.class, book.getIdBook());
    }

    public Book getBookByIdWithBooksAuthors(Book book)
    {
        Book bookWithAuthors = em.find(Book.class, book.getIdBook());
        Hibernate.initialize(bookWithAuthors.getBooksAuthors());
        return bookWithAuthors;
    }

    public Book getBookByIdWithHirings(Book book)
    {
        Book bookWithHirings = em.find(Book.class, book.getIdBook());
        Hibernate.initialize(bookWithHirings.getHirings());

        return bookWithHirings;
    }

    public List<Book> getBookByTitle(String title)
    {
        return em.createNamedQuery("book.byTitle").setParameter("title", title).getResultList();
    }


    public Book updateBook(Book book)
    {
        return (Book) em.merge(book);
    }


    public void deleteBook(Book book)
    {
        em.remove(em.getReference(Book.class, book.getIdBook()));
    }

    public Book addBook(Book book)
    {
        em.persist(book);
        em.flush();
        return book;
    }


}
