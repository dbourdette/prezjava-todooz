package fr.todooz.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Path("/hi/{username}")
public class HelloWorldResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hi(@PathParam("username") String username) {
        return "Hello " + StringUtils.defaultIfEmpty(username, "unknown");
    }
}
