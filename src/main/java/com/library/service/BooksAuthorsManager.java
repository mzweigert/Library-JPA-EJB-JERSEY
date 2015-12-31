package com.library.service;


import com.library.BooksAuthorsDAO;
import com.library.domain.BooksAuthors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class BooksAuthorsManager implements BooksAuthorsDAO
{

    @PersistenceContext
    EntityManager em;

    public List<BooksAuthors> getAllBooksAuthors()
    {
        return em.createNamedQuery("booksAuthors.all").getResultList();
    }

    public BooksAuthors getBooksAuthorsById(BooksAuthors booksAuthors)
    {
        return em.find(BooksAuthors.class, booksAuthors.getIdBooksAuthors());
    }


    public BooksAuthors updateBooksAuthors(BooksAuthors booksAuthors)
    {
        return  (BooksAuthors)em.merge(booksAuthors);
    }

    public void deleteBooksAuthors(BooksAuthors booksAuthors)
    {
        em.remove(em.getReference(BooksAuthors.class, booksAuthors.getIdBooksAuthors()));
    }

    public BooksAuthors addBooksAuthors(BooksAuthors booksAuthors)
    {

        em.persist(booksAuthors);
        em.flush();

        return booksAuthors;
    }


}
