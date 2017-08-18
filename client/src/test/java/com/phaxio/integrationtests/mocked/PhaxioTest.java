package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.Country;
import com.phaxio.exceptions.*;
import com.phaxio.helpers.Responses;
import com.phaxio.restclient.entities.RestRequest;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class PhaxioTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test(expected = AuthenticationException.class)
    public void throwsExceptionOnInvalidCredentials () {
        String error = Responses.json("/error_authentication.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=BAD_SECRET&api_key=BAD_KEY"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(error)));

        Phaxio phaxio = new Phaxio("BAD_KEY", "BAD_SECRET", "http://localhost:%s/v2/", TEST_PORT);

        phaxio.account.status();
    }

    @Test(expected = ApiConnectionException.class)
    public void throwsApiExceptionWhenCannotConnect () {
        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://not_real:%s/v2/", 80);

        phaxio.account.status();
    }

    @Test(expected = InvalidRequestException.class)
    public void throwsInvalidRequestExceptionOnBadRequest () {
        String error = Responses.json("/error_invalid_entity.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(error)));


        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        phaxio.account.status();
    }

    @Test(expected = NotFoundException.class)
    public void throwsNotFoundExceptionOn404 () {
        String error = Responses.json("/error_not_found.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(error)));


        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        phaxio.account.status();
    }

    @Test(expected = RateLimitException.class)
    public void throwsRateLimitExceptionOn429 () {
        String error = Responses.json("/error_rate_limited.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(429)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(error)));


        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        phaxio.account.status();
    }

    @Test(expected = ServerException.class)
    public void throwsServerExceptionOn500 () {
        String error = Responses.json("/error_service.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(error)));


        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        phaxio.account.status();
    }

    @Test
    public void listPages () throws IOException {
        String page1 = Responses.json("/paging_page_1.json");
        String page2 = Responses.json("/paging_page_2.json");

        stubFor(get(urlEqualTo("/v2/public/countries?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(page1)));


        stubFor(get(urlEqualTo("/v2/public/countries?api_secret=SECRET&api_key=KEY&page=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(page2)));


        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        RestRequest request = new RestRequest();
        request.resource = "public/countries";

        Iterable<Country> iterable = client.list(request, Country.class);

        ArrayList<Country> countries = new ArrayList<Country>();

        for (Country country : iterable) {
            countries.add(country);
        }

        assertEquals(6, countries.size());
    }
}
