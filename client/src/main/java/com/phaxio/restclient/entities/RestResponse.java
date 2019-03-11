package com.phaxio.restclient.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class RestResponse
{
    private HttpURLConnection conn;
    private int status = 0;
    private String content;
    private Exception exception;
    private JsonNode jsonNode;
    private ObjectMapper objectMapper;
    private byte[] rawBytes;
    private Map<String, List<String>> headers;

    public RestResponse() { }

    public void setHttpURLConnectionAndRead (HttpURLConnection httpConnection) throws IOException {
        this.conn = httpConnection;
        readEntireResponse();
    }

    public String getContentType() {
        return headers.get("Content-Type").get(0);
    }

    public int getStatusCode() throws IOException {
        return status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getContent() throws IOException {
        if (content == null) {
            content = new String(rawBytes, "UTF-8");
        }

        return content;
    }

    public byte[] getRawBytes() throws IOException {
        return rawBytes;
    }

    public JsonNode toJson() throws IOException {
        if (jsonNode == null) {
            jsonNode = getMapper().readTree(getContent());
        }

        return jsonNode;
    }

    public ObjectMapper getMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper;
    }

    // We need to populate the response before we close the connection
    private void readEntireResponse () throws IOException {
        status = conn.getResponseCode();
        headers = conn.getHeaderFields();
        rawBytes = IOUtils.toByteArray(getInputStream());
    }

    private InputStream getInputStream() throws IOException {
        return getStatusCode() == 200 || getStatusCode() == 201 ? conn.getInputStream() : conn.getErrorStream();
    }
}