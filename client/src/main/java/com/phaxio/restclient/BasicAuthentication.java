package com.phaxio.restclient;

public class BasicAuthentication {
    public final String username;
    public final String password;

    public BasicAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toHeader () {
        String authstring = username + ":" + password;
        return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authstring.getBytes());
    }
}
