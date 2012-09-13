package com.phaxio.test.util;

import com.google.gson.JsonObject;
import com.phaxio.util.HttpRequestHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jnankin
 */
public class HttpRequestHelperTest {

    @Test
    public void simpleTest() throws ProtocolException, MalformedURLException, IOException{
        HttpRequestHelper helper = new HttpRequestHelper();
        InputStream is = helper.doRequest("https://api.phaxio.com", null, "GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String result = br.readLine();
        assertEquals("<h1>Phaxio API Server</h1>", result);
    }
}