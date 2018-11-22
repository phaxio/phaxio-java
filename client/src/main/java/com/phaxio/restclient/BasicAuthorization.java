package com.phaxio.restclient;

import org.apache.commons.codec.binary.Base64;

public class BasicAuthorization {
    public final String username;
    public final String password;

    public BasicAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toHeader () {
        String authstring = username + ":" + password;
        return "Basic " + Base64.encodeBase64String(authstring.getBytes());
    }
}
