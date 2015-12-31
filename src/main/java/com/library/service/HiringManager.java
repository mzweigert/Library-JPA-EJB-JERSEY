package com.library.service;


import com.library.HiringDAO;
import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class HiringManager implements HiringDAO
{

    @PersistenceContext
    EntityManager em;


    public List<Hiring> getAllHirings()
    {
        return em.createNamedQuery("hiring.all").getResultList();
    }

    public Hiring getHiringById(Hiring hiring)
    {
        return em.find(Hiring.class, hiring.getIdHiring());
    }

    public List<Hiring> getHiringsByIdReader(Reader reader)
    {
        return em.createNamedQuery("hiring.byIdReader").setParameter("idReader", reader.getIdReader()).getResultList();
    }

    public List<Hiring> getHiringsByIdBook(Book book)
    {
        return em.createNamedQuery("hiring.byIdBook").setParameter("idBook", book.getIdBook()).getResultList();
    }

    public Hiring updateHiring(Hiring hiring)
    {
        return (Hiring) em.merge(hiring);
    }

    public void deleteHiring(Hiring hiring)
    {
        em.remove(em.getReference(Hiring.class, hiring.getIdHiring()));
    }

    public Hiring addHiring(Hiring hiring)
    {
        em.persist(hiring);
        em.flush();
        return hiring;

    }
}
