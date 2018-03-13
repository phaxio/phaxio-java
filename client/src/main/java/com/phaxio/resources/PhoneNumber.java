package com.phaxio.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phaxio.services.Requests;
import com.phaxio.restclient.entities.RestRequest;

import java.util.Date;

/**
 * A phone number that you can use to send faxes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumber
{
    private Requests client;

    public void setClient (Requests client) {
        this.client = client;
    }

    @JsonProperty("phone_number")
    public String number;

    @JsonProperty("city")
    public String city;

    @JsonProperty("state")
    public String state;

    @JsonProperty("country")
    public String country;

    @JsonProperty("cost")
    public int cost;

    @JsonProperty("last_billed_at")
    public Date lastBilled;

    @JsonProperty("provisioned_at")
    public Date provisioned;

    @JsonProperty("callback_url")
    public String callbackUrl;

    /**
     * Releases a number
     */
    public void release()
    {
        RestRequest request = new RestRequest();
        request.resource = "phone_numbers/" + number;

        client.delete(request);
    }
}
