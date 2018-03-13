package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.entities.Country;
import com.phaxio.helpers.Responses;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CountryTests {
    @Test
    public void ignoresExtraFields () throws IOException {
        String json = Responses.json("/jsonobjects/country_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        Country country = mapper.readValue(json, Country.class);

        assertEquals("United States", country.name);
        assertEquals("US", country.alpha2);
        assertEquals("1", country.countryCode);
        assertEquals(7, country.pricePerPage);
        assertEquals("full", country.sendSupport);
        assertEquals("fullest", country.receiveSupport);
    }
}
