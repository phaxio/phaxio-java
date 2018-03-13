package com.phaxio.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaxio.entities.Account;
import com.phaxio.helpers.Responses;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void ignoresExtraFields () throws IOException {
        String json = Responses.json("/jsonobjects/account_status_extra_fields.json");

        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(json, Account.class);

        assertEquals(5050, account.balance);
        assertEquals(15, account.faxesThisMonth.sent);
        assertEquals(7, account.faxesThisMonth.received);
    }
}
