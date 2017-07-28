package com.phaxio;


import com.phaxio.repositories.*;
import com.phaxio.services.Requests;

/**
 * The Phaxio API client.
 */
public class Phaxio {
    private static final String PHAXIO_ENDPOINT = "https://api.phaxio.com:%s/v2/";
    private static final int PHAXIO_PORT = 443;

    public Phaxio(String key, String secret) {
        this(key, secret, PHAXIO_ENDPOINT, PHAXIO_PORT);
    }

    public Phaxio(String key, String secret, String endpoint, int port) {
        Requests requests = new Requests(key, secret, endpoint, port);

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
