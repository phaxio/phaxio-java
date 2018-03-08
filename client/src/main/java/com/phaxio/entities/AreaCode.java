package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the area codes where you can purchase numbers.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaCode
{
    @JsonProperty("country_code")
    public String countryCode;

    @JsonProperty("area_code")
    public String areaCode;

    @JsonProperty("city")
    public String city;

    @JsonProperty("state")
    public String state;

    @JsonProperty("country")
    public String country;

    @JsonProperty("toll_free")
    public boolean tollFree;
}