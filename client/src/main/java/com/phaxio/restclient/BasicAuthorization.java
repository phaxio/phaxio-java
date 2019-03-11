package com.phaxio.restclient;

import java.io.UnsupportedEncodingException;
import com.google.common.io.BaseEncoding;

public class BasicAuthorization {
    public final String username;
    public final String password;

    public BasicAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toHeader () {
        String authstring = username + ":" + password;
        String encodedString = "";

        try {
            encodedString = BaseEncoding.base64().encode(authstring.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException exception) { }

        return "Basic " + encodedString;
    }
}
