package com.phaxio.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Fax
{
    @JsonProperty("id")
    public int id;

    @JsonProperty("direction")
    public String direction;

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
}