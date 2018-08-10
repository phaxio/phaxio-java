package com.phaxio.helpers;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.phaxio.restclient.BasicAuthorization;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class Auth {
    public static final String VALID_KEY = "KEY";
    public static final String VALID_SECRET = "SECRET";
    public static final StringValuePattern VALID_AUTH_MATCHER = equalTo(new BasicAuthorization(VALID_KEY, VALID_SECRET).toHeader());

    public static StringValuePattern authMatcher(String username, String password) {
        return equalTo(new BasicAuthorization(username, password).toHeader());
    }
}
