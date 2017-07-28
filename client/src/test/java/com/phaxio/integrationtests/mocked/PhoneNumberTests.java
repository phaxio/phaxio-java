package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.PhoneNumber;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PhoneNumberTests {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void deletesFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(delete(urlEqualTo("/v2/phone_numbers/8088675308?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        PhoneNumber number = new PhoneNumber();
        number.number = "8088675308";
        number.setClient(client);

        number.release();

        verify(deleteRequestedFor(urlEqualTo("/v2/phone_numbers/8088675308?api_secret=SECRET&api_key=KEY")));
    }
}
