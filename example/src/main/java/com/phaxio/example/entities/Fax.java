package com.phaxio.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Fax
{
    @JsonProperty("id")
    public int id;

    @JsonProperty("caller_name")
    public String callerName;

    @JsonProperty("barcodes")
    public List<Barcode> barcodes;

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

    @JsonProperty("requested_at")
    public Date requestedAt;

    @JsonProperty("to_number")
    public String toNumber;

    @JsonProperty("from_number")
    public String fromNumber;

    @JsonProperty("caller_id")
    public String callerId;

    @JsonProperty("recipients")
    public List<Recipient> recipients;

    @JsonProperty("tags")
    public Map<String, String> tags;

    @JsonProperty("error_id")
    public int errorId;

    @JsonProperty("error_type")
    public String errorType;

    @JsonProperty("error_code")
    public String errorCode;

    @JsonProperty("completed_at")
    public Date completedAt;
}