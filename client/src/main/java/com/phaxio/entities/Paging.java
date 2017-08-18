package com.phaxio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paging {
    @JsonProperty("total")
    public int total;

    @JsonProperty("per_page")
    public int perPage;

    @JsonProperty("page")
    public int page;
}
