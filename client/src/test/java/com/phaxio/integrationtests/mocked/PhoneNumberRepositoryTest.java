package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.helpers.Auth;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.PhoneNumber;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class PhoneNumberRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void createsNumber () throws IOException, ParseException {
        String json = Responses.json("/provision_number.json");

        stubFor(post(urlEqualTo("/ver/phone_numbers"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhoneNumber number = phaxio.phoneNumber.create("1", "808");

        assertEquals("+18475551234", number.number);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date lastBilledAt = format.parse("2016-06-16T15:45:32-0600");
        Date provisionedAt = format.parse("2016-06-16T15:45:32-0600");

        assertEquals("Northbrook", number.city);
        assertEquals("Illinois", number.state);
        assertEquals("United States", number.country);
        assertEquals(200, number.cost);
        assertEquals(lastBilledAt, number.lastBilled);
        assertEquals(provisionedAt, number.provisioned);
        assertEquals("http://example.com", number.callbackUrl);
    }

    @Test
    public void retrievesNumber () throws IOException {
        String json = Responses.json("/retrieve_number.json");

        stubFor(get(urlEqualTo("/ver/phone_numbers/18475551234"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        PhoneNumber number = phaxio.phoneNumber.retrieve("18475551234");

        assertEquals("+18475551234", number.number);
    }

    @Test
    public void listsNumber () throws IOException {
        String json = Responses.json("/list_numbers.json");

        stubFor(get(urlEqualTo("/ver/phone_numbers"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        Iterable<PhoneNumber> numbers = phaxio.phoneNumber.list();

        List<PhoneNumber> numberList = new ArrayList<PhoneNumber>();

        for (PhoneNumber number : numbers) {
            numberList.add(number);
        }

        assertEquals(2, numberList.size());
    }
}
