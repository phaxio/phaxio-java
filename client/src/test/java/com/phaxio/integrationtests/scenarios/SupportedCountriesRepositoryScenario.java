package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.entities.Country;
import com.phaxio.helpers.Config;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SupportedCountriesRepositoryScenario extends RateLimitedScenario {
    @Test
    public void listsCountries () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        Iterable<Country> countries = phaxio.publicInfo.supportedCountry.list();

        List<Country> countryList = new ArrayList<Country>();

        for (Country country : countries) {
            countryList.add(country);
        }

        assertTrue(countryList.size() > 40);
    }
}
