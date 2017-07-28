package com.phaxio.restclient.entities;

public class Parameter {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private String name;
    private Object value;
    private String contentType;

    public Parameter (String name, Object value, String contentType) {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }
}
