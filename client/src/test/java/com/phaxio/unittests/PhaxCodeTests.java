package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.resources.PhaxCode;
import com.phaxio.helpers.Responses;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PhaxCodeTests {
    @Test
    public void ignoresExtraFields () throws IOException {
        String json = Responses.json("/jsonobjects/phax_code_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        PhaxCode phaxCode = mapper.readValue(json, PhaxCode.class);

        assertEquals("1234", phaxCode.identifier);
    }
}
