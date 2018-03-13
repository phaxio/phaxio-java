package com.phaxio.resources;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phaxio.services.Requests;
import com.phaxio.restclient.RestClient;
import com.phaxio.restclient.entities.RestRequest;

import java.util.Date;

/**
 * A PhaxCode is a barcode that Phaxio generates, which can be embedded with metadata.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhaxCode
{
    private Requests client;

    public void setClient (Requests client) {
        this.client = client;
    }

    @JsonProperty("identifier")
    public String identifier;

    @JsonProperty("metadata")
    public String metadata;

    @JsonProperty("created_at")
    public Date createdAt;

    /**
     * Returns a byte array representing PNG of the PhaxCode.
     * @return A byte array
     */
    public byte[] png()
    {
        RestRequest request = new RestRequest();

        String resource = "phax_code";

        if (identifier != null)
        {
            resource += "s/" + RestClient.escape(identifier);
        }

        request.resource = resource + ".png";

        return client.download(request);
    }
}