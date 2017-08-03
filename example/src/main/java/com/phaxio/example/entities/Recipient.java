package com.phaxio.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Recipient
{
    @JsonProperty("number")
    public String phoneNumber;

    @JsonProperty("status")
    public String status;

    @JsonProperty("retry_count")
    public int retryCount;

    @JsonProperty("completed_at")
    public Date completedAt;

    @JsonProperty("bitrate")
    public int bitrate;

    @JsonProperty("resolution")
    public int resolution;

    @JsonProperty("error_type")
    public String errorType;

    @JsonProperty("error_id")
    public int errorId;

    @JsonProperty("error_message")
    public String errorMessage;
}
