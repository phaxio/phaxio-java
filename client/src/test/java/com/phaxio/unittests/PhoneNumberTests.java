package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.PhoneNumber;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PhoneNumberTests {
    @Test
    public void ignoresExtraFields () throws IOException, ParseException {
        String json = Responses.json("/jsonobjects/phone_number_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        PhoneNumber number = mapper.readValue(json, PhoneNumber.class);

        assertEquals("+18475551234", number.number);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date lastBilledAt = format.parse("2016-05-10T11:38:15-0500");
        Date provisionedAt = format.parse("2016-03-10T11:38:15-0600");

        assertEquals("Northbrook", number.city);
        assertEquals("Illinois", number.state);
        assertEquals("United States", number.country);
        assertEquals(200, number.cost);
        assertEquals(lastBilledAt, number.lastBilled);
        assertEquals(provisionedAt, number.provisioned);
        assertEquals("http://example.com", number.callbackUrl);
    }
}
