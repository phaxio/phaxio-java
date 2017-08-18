package com.phaxio.repositories;


import com.phaxio.services.Requests;
import com.phaxio.resources.PhaxCode;
import com.phaxio.restclient.RestClient;
import com.phaxio.restclient.entities.RestRequest;

public class PhaxCodeRepository {
    private Requests client;

    public PhaxCodeRepository (Requests client) {
        this.client = client;
    }

    /**
     * Creates a PhaxCode and returns an identifier for the barcode image.
     * @param metadata Metadata to associate with this code.
     * @return A PhaxCode object.
     */
    public PhaxCode create(String metadata)
    {
        RestRequest request = new RestRequest();

        request.resource = "phax_codes.json";

        request.addParameter("metadata", metadata);

        return client.post(request, PhaxCode.class);
    }

    /**
     * Retrieves the default PhaxCode
     * @return A PhaxCode object
     */
    public PhaxCode retrieve() {
        return retrieve(null);
    }

    /**
     * Retrieves a PhaxCode by ID
     * @param id The ID to find
     * @return A PhaxCode object
     */
    public PhaxCode retrieve(String id) {
       RestRequest request = new RestRequest();

       String resource = "phax_code";

        if (id != null)
        {
            resource += "s/" + RestClient.escape(id);
        }

        request.resource = resource + ".json";

        return client.get(request, PhaxCode.class);
    }
}
