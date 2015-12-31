package com.library.rest;

import com.library.AuthorDAO;
import com.library.domain.Author;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("/author")
public class AuthorResource
{

    @EJB
    private AuthorDAO authorManager;

    @POST
    @Path("/addAuthor")
    public Response addAuthor(@FormParam("name") String name, @FormParam("surname") String surname)
    {
        Author author = new Author(name, surname);
        authorManager.addAuthor(author);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/getAllAuthors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors()
    {
        return authorManager.getAllAuthors();
    }

    @PUT
    @Path("/updateAuthor")
    public Response updateAuthor(@FormParam("idAuthor") long idAuthor, @FormParam("name") String name, @FormParam("surname") String surname)
    {
        Author author = new Author(name, surname);
        author.setIdAuthor(idAuthor);

        authorManager.updateAuthor(author);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/deleteAuthor")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAuthor(@FormParam("idAuthor") long idAuthor)
    {
        Author author = new Author();
        author.setIdAuthor(idAuthor);

        if(authorManager.getAuthorByIdWithBooksAuthors(author).getBooksAuthors().size() > 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("Author has relation in BookAuthors table").build();


        authorManager.deleteAuthor(author);
        return Response.status(Response.Status.OK).build();

    }

}
