package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.fixtures.BinaryFixtures;
import com.phaxio.helpers.Auth;
import com.phaxio.resources.PhaxCode;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertArrayEquals;

public class PhaxCodeTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void retrievePhaxCodePng () throws IOException {
        byte[] expectedBytes = BinaryFixtures.getTestPhaxCode();

        stubFor(get(urlEqualTo("/ver/phax_codes/1234.png"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/octet")
                        .withBody(expectedBytes)));

        Requests client = new Requests(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhaxCode code = new PhaxCode();

        code.identifier = "1234";
        code.setClient(client);

        byte[] retrievedBytes = code.png();

        assertArrayEquals(expectedBytes, retrievedBytes);
    }

    @Test
    public void retrieveDefaultPhaxCodePng () throws IOException {
        byte[] expectedBytes = BinaryFixtures.getTestPhaxCode();

        stubFor(get(urlEqualTo("/ver/phax_code.png"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/octet")
                        .withBody(expectedBytes)));

        Requests client = new Requests(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhaxCode code = new PhaxCode();

        code.setClient(client);

        byte[] retrievedBytes = code.png();

        assertArrayEquals(expectedBytes, retrievedBytes);
    }
}
