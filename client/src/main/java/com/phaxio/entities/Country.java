package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the state of Phaxio support for a country.
 */
public class Country
{
    @JsonProperty("name")
    public String name;

    @JsonProperty("alpha2")
    public String alpha2;

    @JsonProperty("country_code")
    public String countryCode;

    @JsonProperty("price_per_page")
    public int pricePerPage;

    @JsonProperty("send_support")
    public String sendSupport;

    @JsonProperty("receive_support")
    public String receiveSupport;
}