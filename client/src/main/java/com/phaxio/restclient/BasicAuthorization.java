package com.phaxio.restclient;

public class BasicAuthorization {
    public final String username;
    public final String password;

    public BasicAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toHeader () {
        String authstring = username + ":" + password;
        return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authstring.getBytes());
    }
}
