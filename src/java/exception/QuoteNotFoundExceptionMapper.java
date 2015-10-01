package exception;

import com.google.gson.JsonObject;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Tobias Jacobsen
 */
@Provider
public class QuoteNotFoundExceptionMapper implements ExceptionMapper<QuoteNotFoundException> {

    @Context
    ServletContext context;

    @Override
    public Response toResponse(QuoteNotFoundException e) {
        JsonObject jo = new JsonObject();
        if (Boolean.valueOf(context.getInitParameter("debug"))) {
            jo.addProperty("StackTrace", Arrays.toString(e.getStackTrace()));
        }
        jo.addProperty("code", 404);
        jo.addProperty("message", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
