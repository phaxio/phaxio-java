package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.entities.Barcode;
import com.phaxio.entities.Recipient;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.Fax;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FaxTests {
    @Test
    public void ignoresExtraFields () throws IOException, ParseException {
        String json = Responses.json("/jsonobjects/fax_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        Fax fax = mapper.readValue(json, Fax.class);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date createdAt = format.parse("2015-09-02T11:28:02-0500");
        Date completedAt = format.parse("2015-09-02T11:28:54-0500");

        assertEquals(123456, fax.id);
        assertEquals("Alice", fax.callerName);

        Barcode barcode = fax.barcodes.get(0);
        assertEquals("barcode-type-1", barcode.type);
        assertEquals(1, barcode.page);
        assertEquals("barcode-value-1", barcode.value);
        assertEquals("phax-code-id-1", barcode.identifier);
        assertEquals("phax-code-metadata-1", barcode.metadata);

        assertEquals("sent", fax.direction);
        assertEquals(3, fax.pageCount);
        assertEquals("success", fax.status);
        assertTrue(fax.isTest);
        assertEquals(createdAt, fax.createdAt);
        assertEquals(completedAt, fax.completedAt);
        assertEquals(21, fax.costInCents);
        assertEquals("123", fax.fromNumber);
        assertEquals("1234", fax.tags.get("order_id"));
        assertEquals("321", fax.toNumber);
        assertEquals("Caller", fax.callerId);
        assertEquals(42, fax.errorId);
        assertEquals("error_type", fax.errorType);
        assertEquals("error_message", fax.errorMessage);

        Recipient recipient = fax.recipients.get(0);

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
