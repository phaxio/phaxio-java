package com.phaxio.repositories;

import com.phaxio.entities.Account;
import com.phaxio.restclient.entities.RestRequest;
import com.phaxio.services.Requests;

public class AccountRepository {
    private Requests client;

    public AccountRepository(Requests client)
    {
        this.client = client;
    }

    /**
     * Gets the account for this Phaxio instance.
     * @return An Account object
     */
    public Account status() {
        RestRequest request = new RestRequest();
        request.setResource("account/status");

        return client.get(request, Account.class);
    }
}
