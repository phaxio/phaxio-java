package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents how many faxes have been sent and received.
 */
public class FaxCount
{
    @JsonProperty("sent")
    public int sent;

    @JsonProperty("received")
    public int received;
}