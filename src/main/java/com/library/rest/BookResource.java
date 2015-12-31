package com.library.rest;

import com.library.AuthorDAO;
import com.library.BookDAO;
import com.library.domain.Author;
import com.library.domain.Book;
import com.library.service.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Stateless
@Path("/book")
public class BookResource
{

    @EJB
    private BookDAO bookManager;

    @POST
    @Path("/addBook")
    public Response addBook(@FormParam("title") String title, @FormParam("relaseDate") Date relaseDate, @FormParam("relase") int relase)
    {
        Book book = new Book(title, relaseDate, relase);
        bookManager.addBook(book);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/getAllBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks()
    {
        return bookManager.getAllBooks();
    }

    @PUT
    @Path("/updateBook")
    public Response updateBook(@FormParam("idBook")long idBook, @FormParam("title") String title, @FormParam("relaseDate") Date relaseDate, @FormParam("relase") int relase)
    {
        Book book = new Book(title, relaseDate, relase);
        book.setIdBook(idBook);

        bookManager.updateBook(book);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/deleteBook")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(@FormParam("idBook") long idBook)
    {
        Book book = new Book();
        book.setIdBook(idBook);

        if(bookManager.getBookByIdWithBooksAuthors(book).getBooksAuthors().size() > 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("Book has relation in BookAuthors table").build();

        if(bookManager.getBookByIdWithHirings(book).getHirings().size() > 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("Book has relation in Hiring table").build();

        bookManager.deleteBook(book);

        return Response.status(Response.Status.OK).build();
    }

}
