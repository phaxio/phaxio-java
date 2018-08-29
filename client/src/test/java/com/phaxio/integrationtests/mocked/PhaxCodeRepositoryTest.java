package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.helpers.Auth;
import com.phaxio.resources.PhaxCode;
import com.phaxio.helpers.Responses;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class PhaxCodeRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void createPhaxCode () throws IOException {
        String json = Responses.json("/phax_code.json");

        stubFor(post(urlEqualTo("/ver/phax_codes.json"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhaxCode code = phaxio.phaxCode.create("1234");

        assertEquals("1234", code.identifier);
    }

    @Test
    public void retrieveDefaultPhaxCode () throws IOException, ParseException {
        String json = Responses.json("/phax_code.json");

        stubFor(get(urlEqualTo("/ver/phax_code.json"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhaxCode code = phaxio.phaxCode.retrieve();

        assertEquals("1234", code.identifier);
        assertEquals("some_stuff", code.metadata);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date createdAt = format.parse("2015-09-02T11:28:02-0500");

        assertEquals(createdAt, code.createdAt);
    }

    @Test
    public void retrievePhaxCode () throws IOException {
        String json = Responses.json("/phax_code.json");

        stubFor(get(urlEqualTo("/ver/phax_codes/1234.json"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhaxCode code = phaxio.phaxCode.retrieve("1234");

        assertEquals("1234", code.identifier);
    }
}
