package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.entities.Recipient;
import com.phaxio.helpers.Responses;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RecipientTests {
    @Test
    public void ignoresExtraFields () throws IOException, ParseException {
        String json = Responses.json("/jsonobjects/recipient_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        Recipient recipient = mapper.readValue(json, Recipient.class);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date completedAt = format.parse("2015-09-02T11:28:54-0500");

        assertEquals("+14141234567", recipient.phoneNumber);
        assertEquals(completedAt, recipient.completedAt);
        assertEquals("success", recipient.status);
        assertEquals(41, recipient.errorId);
        assertEquals("recipient_error_type", recipient.errorType);
        assertEquals("recipient_error_message", recipient.errorMessage);
        assertEquals(3, recipient.retryCount);
        assertEquals(14400, recipient.bitrate);
        assertEquals(8040, recipient.resolution);
    }
}
