/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.phaxio;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.phaxio.util.HttpRequestHelper;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author jnankin
 */
public class Phaxio {
    public static String apiKey;
    public static String apiSecret;

    private static String host = "https://api.phaxio.com/";
    private static String version = "v1";


    public static JsonObject doRequest(String operation, Map<String, Object> params, String method) {
        params.put("api_key", apiKey);
        params.put("api_secret", apiSecret);

        String url = host + version + "/" + operation;

        try {
            HttpRequestHelper helper = new HttpRequestHelper();
            String result = helper.doRequest(url, params, method);
            
            if (helper.getContentType() == 'application/json'){
                JsonObject object = new JsonObject();
                Gson gson = new Gson();
                gson.
            }
        }
        catch(Exception e){

        }

    }


}
