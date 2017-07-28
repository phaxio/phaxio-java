package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.Country;
import com.phaxio.helpers.Responses;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class SupportedCountriesRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void listsCountries () throws IOException {
        String json = Responses.json("/supported_countries.json");

        stubFor(get(urlEqualTo("/v2/public/countries?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Iterable<Country> countries = phaxio.publicInfo.supportedCountry.list();

        List<Country> countryList = new ArrayList<Country>();

        for (Country country : countries) {
            countryList.add(country);
        }

        assertEquals(3, countryList.size());

        Country country = countryList.get(0);

        assertEquals("United States", country.name);
        assertEquals("US", country.alpha2);
        assertEquals("1", country.countryCode);
        assertEquals(7, country.pricePerPage);
        assertEquals("full", country.sendSupport);
        assertEquals("fullest", country.receiveSupport);
    }
}
