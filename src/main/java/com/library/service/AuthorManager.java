package com.library.service;


import com.library.AuthorDAO;
import com.library.domain.Author;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless

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
    public Author getAuthorByIdWithBooksAuthors(Author author)
    {
        author = em.find(Author.class, author.getIdAuthor());
        Hibernate.initialize(author.getBooksAuthors());
        return author;
    }
    public List<Author> getAuthorBySurname(String surname)
    {
        return em.createNamedQuery("author.bySurname").setParameter("surname", surname).getResultList();
    }

    public Author updateAuthor(Author author)
    {
        return  (Author)em.merge(author);
    }

    public void deleteAuthor(Author author)
    {
        em.remove(em.getReference(Author.class, author.getIdAuthor()));
    }

    public Author addAuthor(Author author)
    {

        em.persist(author);
        em.flush();

        return author;
    }


}
