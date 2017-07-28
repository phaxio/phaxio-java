package com.phaxio.restclient.entities;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RestRequest  {
    public final List<FileParameter> files;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public InputStream getResponseWriter() {
        return responseWriter;
    }

    public void setResponseWriter(InputStream responseWriter) {
        this.responseWriter = responseWriter;
    }

    public Method method;

    public String resource;

    public List<Parameter> parameters;

    public InputStream responseWriter;

    public RestRequest ()
    {
        parameters = new ArrayList<Parameter>();
        files = new ArrayList<FileParameter>();
    }

    public RestRequest addFile(String name, byte[] bytes, String fileName, String contentType)
    {
        files.add(new FileParameter(name, bytes, fileName, contentType));

        return this;
    }

    public RestRequest addParameter(String name, Object value)
    {
        parameters.add(new Parameter(name, value, null));

        return this;
    }

    public RestRequest addOrReplaceParameter(String name, Object value)
    {
        Parameter existingParameter = null;

        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                existingParameter = param;
            }
        }

        if (existingParameter != null) {
            existingParameter.setValue(value);
        } else {
            parameters.add( new Parameter(name, value, null));
        }

        return this;
    }
}