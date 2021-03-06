package com.library.rest;

import com.library.BookDAO;
import com.library.domain.Book;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(@FormParam("title") String title, @FormParam("relaseDate") Date relaseDate, @FormParam("relase") int relase)
    {
        Book book = new Book(title, relaseDate, relase);
        return bookManager.addBook(book);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(@FormParam("idBook")long idBook, @FormParam("title") String title, @FormParam("relaseDate") Date relaseDate, @FormParam("relase") int relase)
    {
        Book book = new Book(title, relaseDate, relase);
        book.setIdBook(idBook);
        return bookManager.updateBook(book);
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
