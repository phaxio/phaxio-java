package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.Account;
import com.phaxio.helpers.Responses;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class AccountRepositoryTests {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void getsAccountStatus () throws IOException {
        String json = Responses.json("/account_status.json");

        stubFor(get(urlEqualTo("/v2/account/status?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Account account = phaxio.account.status();

        assertEquals(5050, account.balance);
        assertEquals(15, account.faxesThisMonth.sent);
        assertEquals(7, account.faxesThisMonth.received);
    }
}
