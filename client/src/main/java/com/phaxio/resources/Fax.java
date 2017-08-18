package com.phaxio.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phaxio.services.Requests;
import com.phaxio.entities.Recipient;
import com.phaxio.restclient.entities.RestRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Fax
{
    private Requests client;

    private FaxFile file;

    /**
     * Gets the fax's file
     */
    public FaxFile file() {
        if (file == null) {
            file = new FaxFile(id);
            file.setClient(client);
        }

        return file;
    }

    public void setClient (Requests client) {
        this.client = client;
    }

    public Fax()
    {
    }

    @JsonProperty("id")
    public int id;

    @JsonProperty("direction")
    public String direction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("num_pages")
    public int pageCount;

    @JsonProperty("cost")
    public int costInCents;

    @JsonProperty("status")
    public String status;

    @JsonProperty("is_test")
    public boolean isTest;

    @JsonProperty("created_at")
    public Date createdAt;

    @JsonProperty("caller_id")
    public String callerId;

    @JsonProperty("from_number")
    public String fromNumber;

    @JsonProperty("to_number")
    public String toNumber;

    @JsonProperty("recipients")
    public List<Recipient> recipients;

    @JsonProperty("tags")
    public Map<String, String> tags;

    @JsonProperty("error_type")
    public String errorType;

    @JsonProperty("error_id")
    public int errorId;

    @JsonProperty("error_message")
    public String errorMessage;

    @JsonProperty("completed_at")
    public Date completedAt;

    /**
     * Resends this fax
     */
    public void resend()
    {
        resend(null);
    }

    /**
     * Resends this fax
     * @param callbackUrl The URL to send the callback to.
     */
    public void resend(String callbackUrl)
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + id + "/resend";

        if (callbackUrl != null)
        {
            request.addParameter("callback_url", callbackUrl);
        }

        client.post(request);
    }

    /**
     * Cancels this fax
     */
    public void cancel()
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + id + "/cancel";

        client.post(request);
    }

    /**
     * Deletes this fax
     */
    public void delete()
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + id;

        client.delete(request);
    }
}