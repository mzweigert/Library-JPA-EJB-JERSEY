package com.library.service;


import com.library.ReaderDAO;
import com.library.domain.Reader;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class ReaderManager implements ReaderDAO
{
    @PersistenceContext
    EntityManager em;

    public List<Reader> getAllReaders()
    {
        return em.createNamedQuery("reader.all").getResultList();
    }

    public Reader getReaderById(Reader reader)
    {
        return em.find(Reader.class, reader.getIdReader());
    }

    public List<Reader> getReadersBySurname(String surname)
    {
        return em.createNamedQuery("reader.bySurname").setParameter("surname", surname).getResultList();
    }

    public Reader getReaderByIdWithHirings(Reader reader)
    {
        Reader readerWithHirings = em.find(Reader.class, reader.getIdReader());
        Hibernate.initialize(readerWithHirings.getHirings());

        return readerWithHirings;
    }

    public Reader updateReader(Reader reader)
    {
        return em.merge(reader);

    }

    public void deleteReader(Reader reader)
    {
        em.remove(em.getReference(Reader.class, reader.getIdReader()));
    }

    public Reader addReader(Reader reader)
    {
        em.persist(reader);
        em.flush();
        return reader;
    }
}
