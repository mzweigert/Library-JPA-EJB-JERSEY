package com.library.rest;

import com.library.ReaderDAO;
import com.library.domain.Reader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Stateless
@Path("/reader")
public class ReaderResource
{

    @EJB
    private ReaderDAO readerManager;

    @POST
    @Path("/addReader")
    @Produces(MediaType.APPLICATION_JSON)
    public Reader addReader(@FormParam("name") String name,
                              @FormParam("surname") String surname,
                              @FormParam("joinDate") Date joinDate,
                              @FormParam("extraPoints") int extraPoints)
    {
        Reader reader = new Reader(name, surname, joinDate, extraPoints);
        return readerManager.addReader(reader);
    }

    @GET
    @Path("/getAllReaders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reader> getAllReaders()
    {
        return readerManager.getAllReaders();
    }

    @PUT
    @Path("/updateReader")
    @Produces(MediaType.APPLICATION_JSON)
    public Reader updateReader(@FormParam("idReader") long idReader,
                                 @FormParam("name") String name,
                                 @FormParam("surname") String surname,
                                 @FormParam("joinDate") Date joinDate,
                                 @FormParam("extraPoints") int extraPoints)
    {
        Reader reader = new Reader(name, surname, joinDate, extraPoints);
        reader.setIdReader(idReader);
        return readerManager.updateReader(reader);
    }

    @DELETE
    @Path("/deleteReader")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteReader(@FormParam("idReader") long idReader)
    {
        Reader reader = new Reader();
        reader.setIdReader(idReader);

        if(readerManager.getReaderByIdWithHirings(reader).getHirings().size() > 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("Reader has relation in Hiring table").build();


        readerManager.deleteReader(reader);
        return Response.status(Response.Status.OK).build();

    }

}
