package com.library.rest;

import com.library.AuthorDAO;
import com.library.BookDAO;
import com.library.BooksAuthorsDAO;
import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.BooksAuthors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("/booksAuthors")
public class BooksAuthorsResource
{

    @EJB
    private BookDAO bookManager;

    @EJB
    private AuthorDAO authorManager;

    @EJB
    private BooksAuthorsDAO booksAuthorsManager;


    @POST
    @Path("/addBooksAuthors")
    public Response addBooksAuthors(@FormParam("idAuthor") long idAuthor, @FormParam("idBook") long idBook)
    {
        Book bookById = new Book();
        bookById.setIdBook(idBook);
        Author authorById = new Author();
        authorById.setIdAuthor(idAuthor);

        Author author = authorManager.getAuthorById(authorById);
        Book book = bookManager.getBookById(bookById);

        BooksAuthors booksAuthors = new BooksAuthors(author, book);

        booksAuthorsManager.addBooksAuthors(booksAuthors);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/getAllBooksAuthors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BooksAuthors> getAllBooksAuthors()
    {
        return booksAuthorsManager.getAllBooksAuthors();
    }

    @PUT
    @Path("/updateBooksAuthors")
    public Response updateBooksAuthors(@FormParam("idBooksAuthors") long idBooksAuthors, @FormParam("idAuthor") long idAuthor, @FormParam("idBook") long idBook)
    {
        Book bookById = new Book();
        bookById.setIdBook(idBook);
        Author authorById = new Author();
        authorById.setIdAuthor(idAuthor);
        BooksAuthors booksAuthorsById = new BooksAuthors();
        booksAuthorsById.setIdBooksAuthors(idBooksAuthors);

        BooksAuthors booksAuthors = booksAuthorsManager.getBooksAuthorsById(booksAuthorsById);

        Author author = authorManager.getAuthorById(authorById);
        Book book = bookManager.getBookById(bookById);

        booksAuthors.setAuthor(author);
        booksAuthors.setBook(book);

        booksAuthorsManager.updateBooksAuthors(booksAuthors);

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/deleteBooksAuthors")
    public Response deleteBooksAuthors(@FormParam("idBooksAuthors") long idBooksAuthors)
    {
        BooksAuthors booksAuthors = new BooksAuthors();

        booksAuthors.setIdBooksAuthors(idBooksAuthors);
        booksAuthorsManager.deleteBooksAuthors(booksAuthors);

        return Response.status(Response.Status.OK).build();
    }

}
