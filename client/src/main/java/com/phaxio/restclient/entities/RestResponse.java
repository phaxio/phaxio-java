package com.phaxio.restclient.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class RestResponse
{
    private HttpURLConnection conn;
    private int status = 0;
    private String content;

    public RestResponse() { }

    public void setHttpURLConnection (HttpURLConnection httpConnection) {
        this.conn = httpConnection;
    }

    public String getContentType() {
        return conn.getHeaderField("Content-Type");
    }

    public int getStatusCode() throws IOException {
        if (status == 0) {
            status = conn.getResponseCode();
        }

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
            content = Joiner.on("\n").join(IOUtils.readLines(getInputStream(), Charset.forName("UTF-8")));
        }

        return content;
    }

    public byte[] getRawBytes() throws IOException {
        return IOUtils.toByteArray(getInputStream());
    }

    private Exception exception;

    private InputStream getInputStream() throws IOException {
        return getStatusCode() == 200 || getStatusCode() == 201 ? conn.getInputStream() : conn.getErrorStream();
    }

    private JsonNode jsonNode;

    public JsonNode toJson() throws IOException {
        if (jsonNode == null) {
            jsonNode = getMapper().readTree(getContent());
        }

        return jsonNode;
    }

    private ObjectMapper objectMapper;

    public ObjectMapper getMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper;
    }
}