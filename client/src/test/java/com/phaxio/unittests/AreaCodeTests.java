package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.entities.AreaCode;
import com.phaxio.helpers.Responses;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AreaCodeTests {
    @Test
    public void ignoresExtraFields () throws IOException {
        String json = Responses.json("/jsonobjects/area_code_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        AreaCode areaCode = mapper.readValue(json, AreaCode.class);

        assertEquals("1", areaCode.countryCode);
        assertEquals("203", areaCode.areaCode);
        assertEquals("Bridgeport, Danbury, Meriden", areaCode.city);
        assertEquals("Connecticut", areaCode.state);
        assertEquals("United States", areaCode.country);
        assertFalse(areaCode.tollFree);
    }
}
