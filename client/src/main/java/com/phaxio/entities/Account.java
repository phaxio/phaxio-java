package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This represents a Phaxio account
 */
public class Account
{
    @JsonProperty("balance")
    public int balance;

    @JsonProperty("faxes_today")
    public FaxCount faxesToday;

    @JsonProperty("faxes_this_month")
    public FaxCount faxesThisMonth;
}