package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Barcode {
    @JsonProperty("type")
    public String type;

    @JsonProperty("page")
    public int page;

    @JsonProperty("value")
    public String value;

    @JsonProperty("identifier")
    public String identifier;

    @JsonProperty("metadata")
    public String metadata;
}
