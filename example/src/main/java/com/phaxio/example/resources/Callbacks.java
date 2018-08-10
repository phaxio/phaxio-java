package com.phaxio.example.resources;

import com.phaxio.example.entities.Fax;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("callbacks")
public class Callbacks {

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Fax receive(@FormDataParam("filename") InputStream fileInputStream,
                       @FormDataParam("filename") FormDataContentDisposition disposition,
                       @FormDataParam("fax") Fax fax) throws Exception {

        System.out.println("Id: " + fax.id);

        // Save to the DB, send an email, etc

        return fax;
    }
}
