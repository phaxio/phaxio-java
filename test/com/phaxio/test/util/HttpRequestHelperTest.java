package com.phaxio.test.util;

import com.phaxio.util.HttpRequestHelper;
import java.io.IOException;
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
    public void asdfTest() throws ProtocolException, MalformedURLException, IOException{
        assertTrue(true);
        HttpRequestHelper helper = new HttpRequestHelper();
        System.out.println(helper.doRequest("http://www.phaxio.com/images/logo.png", null, "GET"));
    }
}