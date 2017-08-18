package com.phaxio.repositories;

import com.phaxio.services.Requests;
import com.phaxio.entities.Country;
import com.phaxio.restclient.entities.RestRequest;

public class SupportedCountriesRepository {
    private Requests client;

    public SupportedCountriesRepository(Requests client) {
        this.client = client;
    }

    /**
     * Lists supported countries with pricing
     * @return An Iterable of Country objects
     */
    public Iterable<Country> list() {
        RestRequest request = new RestRequest();
        request.resource = "public/countries";

        return client.list(request, Country.class);
    }
}
