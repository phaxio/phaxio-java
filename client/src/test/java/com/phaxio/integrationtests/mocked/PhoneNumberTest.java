package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.helpers.Auth;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.PhoneNumber;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PhoneNumberTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void deletesFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(delete(urlEqualTo("/ver/phone_numbers/8088675308"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhoneNumber number = new PhoneNumber();
        number.number = "8088675308";
        number.setClient(client);

        number.release();

        verify(deleteRequestedFor(urlEqualTo("/ver/phone_numbers/8088675308"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER));
    }
}
