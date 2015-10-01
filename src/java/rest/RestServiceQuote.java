package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exception.QuoteNotFoundException;
import facade.Facade;
import java.util.Random;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * REST Web Service
 *
 * @author Tobias Jacobsen
 */
@Path("/quote")
public class RestServiceQuote {

    Gson gson;
    JsonObject quoteObj;
    private Random random;

    @Context
    private UriInfo context;

    public RestServiceQuote() {
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        random = new Random();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getQuote(@PathParam("id") int index) throws QuoteNotFoundException {
        quoteObj = new JsonObject();
        quoteObj.addProperty("quote", Facade.getQuote(index));
        return Response.status(Response.Status.OK).entity(gson.toJson(quoteObj)).build();
    }

    @GET
    @Path("/random")
    @Produces("application/json")
    public Response getRandomQuote() throws QuoteNotFoundException {
        quoteObj = new JsonObject();
        quoteObj.addProperty("quote", Facade.getRandomQuote());
        return Response.status(Response.Status.OK).entity(gson.toJson(quoteObj)).build();
    }

    @POST
    @Consumes("application/json")
    public Response createQuote(String quoteString) {
        JsonObject jsonObject = new Gson().fromJson(quoteString, JsonObject.class);
        String quote =  jsonObject.get("quote").getAsString();
        Facade.createQuote(quote);
        quoteObj = new JsonObject();
        quoteObj.addProperty("id", Facade.getLastIndex());
        quoteObj.addProperty("quote", quote);
        return Response.status(Response.Status.OK).entity(gson.toJson(quoteObj)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateQuote(@PathParam("id") int id, String quoteString) throws QuoteNotFoundException {
        JsonObject jsonObject = new Gson().fromJson(quoteString, JsonObject.class);
        String quote =  jsonObject.get("quote").getAsString();
        Facade.updateQuote(id, quote);
        quoteObj = new JsonObject();
        quoteObj.addProperty("id", id);
        quoteObj.addProperty("quote", quote);
        return Response.status(Response.Status.OK).entity(gson.toJson(quoteObj)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteQuote(@PathParam("id") int id) throws QuoteNotFoundException {
        String quote = Facade.getQuote(id);
        Facade.deleteQuote(id);
        quoteObj = new JsonObject();
//        quoteObj.addProperty("id", id);
        quoteObj.addProperty("quote", quote);
        return Response.status(Response.Status.OK).entity(gson.toJson(quoteObj)).build();
    }
}
