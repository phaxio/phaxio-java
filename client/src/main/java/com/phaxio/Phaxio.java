package com.phaxio;


import java.net.Proxy;

import com.phaxio.repositories.*;
import com.phaxio.services.Requests;

/**
 * The Phaxio API client.
 */
public class Phaxio {
    private static final String PHAXIO_SERVICE = "https://api.phaxio.com:%s";
    private static final String PHAXIO_VERSION_SEGMENT = "/v2.1/";
    private static final String PHAXIO_ENDPOINT = PHAXIO_SERVICE + PHAXIO_VERSION_SEGMENT;
    private static final int PHAXIO_PORT = 443;

    public Phaxio(String key, String secret) {
    	this(key, secret, PHAXIO_ENDPOINT, PHAXIO_PORT,null);
    }
    
    public Phaxio(String key, String secret, Proxy proxy) {
        this(key, secret, PHAXIO_ENDPOINT, PHAXIO_PORT, proxy);
    }

    public Phaxio(String key, String secret, String endpoint, int port) {
    	this(key, secret, endpoint, port, null);
    }
    
    private Phaxio(String key, String secret, String endpoint, int port, Proxy proxy) {
        Requests requests = new Requests(key, secret, endpoint, port, proxy);

        fax = new FaxRepository(requests);
        publicInfo = new PublicRepository(requests);
        account = new AccountRepository(requests);
        phoneNumber = new PhoneNumberRepository(requests);
        phaxCode = new PhaxCodeRepository(requests);
    }

    public final FaxRepository fax;
    public final PublicRepository publicInfo;
    public final AccountRepository account;
    public final PhoneNumberRepository phoneNumber;
    public final PhaxCodeRepository phaxCode;
}
