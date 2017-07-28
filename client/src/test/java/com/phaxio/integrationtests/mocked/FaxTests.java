package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.Fax;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class FaxTests {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void deletesFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(delete(urlEqualTo("/v2/faxes/1?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = new Fax();
        fax.id = 1;
        fax.setClient(client);

        fax.delete();

        verify(deleteRequestedFor(urlEqualTo("/v2/faxes/1?api_secret=SECRET&api_key=KEY")));
    }

    @Test
    public void cancelsFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(post(urlEqualTo("/v2/faxes/1/cancel"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = new Fax();
        fax.id = 1;
        fax.setClient(client);

        fax.cancel();

        verify(postRequestedFor(urlEqualTo("/v2/faxes/1/cancel")));
    }

    @Test
    public void resendsFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(post(urlEqualTo("/v2/faxes/1/resend"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = new Fax();
        fax.id = 1;
        fax.setClient(client);

        fax.resend();

        verify(postRequestedFor(urlEqualTo("/v2/faxes/1/resend")));
    }

    @Test
    public void resendsFaxWithCallback () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(post(urlEqualTo("/v2/faxes/1/resend"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = new Fax();
        fax.id = 1;
        fax.setClient(client);

        fax.resend("google.com");

        verify(postRequestedFor(urlEqualTo("/v2/faxes/1/resend"))
                .withRequestBody(containing("google.com")));
    }
}
