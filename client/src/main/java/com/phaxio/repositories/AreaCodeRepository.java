package com.phaxio.repositories;

import com.phaxio.services.Requests;
import com.phaxio.entities.AreaCode;
import com.phaxio.restclient.entities.RestRequest;

import java.util.HashMap;
import java.util.Map;

public class AreaCodeRepository
{
    private Requests client;

    public AreaCodeRepository(Requests client)
    {
        this.client = client;
    }

    /**
     * Gets the area codes that you can purchase numbers in
     * @return A list of areacodes
     */
    public Iterable<AreaCode> list()
    {
        return list(new HashMap<String, Object>());
    }

    /**
     * Gets the area codes that you can purchase numbers in
     * @param filters The filters to apply (see the API documentation for parameters)
     * @return A list of areacodes
     */
    public Iterable<AreaCode> list(Map<String, Object> filters)
    {
        RestRequest request = new RestRequest();
        request.resource = "public/area_codes";

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            request.addParameter(entry.getKey(), entry.getValue());
        }

        return client.list(request, AreaCode.class);
    }
}
