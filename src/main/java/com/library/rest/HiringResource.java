package com.library.rest;

import com.library.BookDAO;
import com.library.HiringDAO;
import com.library.ReaderDAO;
import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Stateless
@Path("/hiring")
public class HiringResource
{

    @EJB
    private BookDAO bookManager;

    @EJB
    private ReaderDAO readerManager;

    @EJB
    private HiringDAO hiringManager;


    @POST
    @Path("/addHiring")
    public Response addHiring(@FormParam("idBook") long idBook,
                              @FormParam("idReader") long idReader,
                              @FormParam("hireDate") Date hireDate)
    {
        Book bookById = new Book();
        bookById.setIdBook(idBook);
        Reader readerById = new Reader();
        readerById.setIdReader(idReader);

        Book book = bookManager.getBookById(bookById);
        Reader reader = readerManager.getReaderById(readerById);

        Hiring hiring = new Hiring(book, reader, hireDate);

        hiringManager.addHiring(hiring);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/getAllHirings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hiring> getAllHirings()
    {
        return hiringManager.getAllHirings();
    }

    @PUT
    @Path("/updateHiring")
    public Response updateHiring(@FormParam("idHiring") long idHiring,
                                 @FormParam("idBook") long idBook,
                                 @FormParam("idReader") long idReader,
                                 @FormParam("hireDate") Date hireDate)
    {
        Book bookById = new Book();
        bookById.setIdBook(idBook);
        Reader readerById = new Reader();
        readerById.setIdReader(idReader);
        Hiring hiringById = new Hiring();
        hiringById.setIdHiring(idHiring);

        Hiring hiring = hiringManager.getHiringById(hiringById);

        Reader reader = readerManager.getReaderById(readerById);
        Book book = bookManager.getBookById(bookById);

        hiring.setReader(reader);
        hiring.setBook(book);
        hiring.setHireDate(hireDate);

        hiringManager.updateHiring(hiring);

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/deleteHiring")
    public Response deleteHiring(@FormParam("idHiring") long idHiring)
    {
        Hiring hiring = new Hiring();

        hiring.setIdHiring(idHiring);
        hiringManager.deleteHiring(hiring);

        return Response.status(Response.Status.OK).build();
    }

}
