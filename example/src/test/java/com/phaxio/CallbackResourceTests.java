package com.phaxio;

import com.phaxio.helpers.Fixtures;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CallbackResourceTests {
    @Test
    public void postWithAllParameters() throws Exception {

        final Client client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class)
                .build();
        WebTarget target = client.target("http://localhost:8080/").path("callbacks");

        FileDataBodyPart filePart = new FileDataBodyPart("file", Fixtures.file("/test.pdf"));

        filePart.setContentDisposition(FormDataContentDisposition.name("filename").fileName("test.pdf").build());

        String faxJson = Fixtures.json("/fax.json");

        MultiPart multipartEntity = new FormDataMultiPart()
                .field("fax", faxJson, MediaType.APPLICATION_JSON_TYPE)
                .field("success", "true", MediaType.TEXT_PLAIN_TYPE)
                .field("is_test", "false", MediaType.TEXT_PLAIN_TYPE)
                .field("direction", "received", MediaType.TEXT_PLAIN_TYPE)
                .bodyPart(filePart);

        Response response = target.request().post(
                Entity.entity(multipartEntity, multipartEntity.getMediaType()));
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));

        response.close();
    }
}
