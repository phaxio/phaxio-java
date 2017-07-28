package com.phaxio.example.resources;

import com.phaxio.example.entities.Fax;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("callbacks")
public class Callbacks {

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response receive(@FormDataParam("filename") InputStream fileInputStream,
                            @FormDataParam("filename") FormDataContentDisposition disposition,
                            @FormDataParam("success") boolean success,
                            @FormDataParam("is_test") boolean isTest,
                            @FormDataParam("direction") String direction,
                            @FormDataParam("fax") Fax fax) throws Exception {

        // Save to the DB, send an email, etc

        return Response.ok("Fax callback received.").build();
    }
}
