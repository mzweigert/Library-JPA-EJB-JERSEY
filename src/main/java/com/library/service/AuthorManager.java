package com.library.service;


import com.library.AuthorDAO;
import com.library.domain.Author;
import com.library.domain.Book;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
@Named("authorManager")
public class AuthorManager implements AuthorDAO
{

    @PersistenceContext
    EntityManager em;

    public List<Author> getAllAuthors()
    {
        return em.createNamedQuery("author.all").getResultList();
    }
    public Author getAuthorById(Author author)
    {
        return em.find(Author.class, author.getIdAuthor());
    }
    public Author getAuthorByIdWithBooks(Author author)
    {
        Author authorWithBooks = em.find(Author.class, author.getIdAuthor());
        authorWithBooks.getBooks();
        return authorWithBooks;

    }
    public List<Author> getAuthorBySurname(String surname)
    {
        return em.createNamedQuery("author.bySurname").setParameter("surname", surname).getResultList();
    }

    public List<Book> getAuthorBooks(Author author)
    {
        Author authorWithBooks = em.find(Author.class, author.getIdAuthor());
        authorWithBooks.getBooks();
        return authorWithBooks.getBooks();
    }

    public Author updateAuthor(Author author)
    {
        return  (Author)em.merge(author);
    }

    public void deleteAuthor(Author author)
    {
        Author temporary = em.find(Author.class, author.getIdAuthor());
        em.remove(temporary);
    }

    public Author addAuthor(Author author)
    {

        em.persist(author);
        em.flush();

        return author;
    }


}
