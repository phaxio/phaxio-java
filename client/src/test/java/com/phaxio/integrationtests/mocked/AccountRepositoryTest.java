package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.Account;
import com.phaxio.helpers.Auth;
import com.phaxio.helpers.Responses;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class AccountRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void getsAccountStatus () throws IOException {
        String json = Responses.json("/account_status.json");

        stubFor(get(urlEqualTo("/ver/account/status"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        Account account = phaxio.account.status();

        assertEquals(5050, account.balance);
        assertEquals(15, account.faxesThisMonth.sent);
        assertEquals(7, account.faxesThisMonth.received);
    }
}
