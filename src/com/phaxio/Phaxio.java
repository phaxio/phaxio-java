/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.phaxio;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phaxio.exception.PhaxioException;
import com.phaxio.util.HttpRequestHelper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    public static String version = "v1";
    
    private static String host = "https://api.phaxio.com/";

    public static JsonObject doRequest(String operation, Map<String, Object> params, String method) throws PhaxioException {
        params.put("api_key", apiKey);
        params.put("api_secret", apiSecret);

        String url = host + version + "/" + operation;

        try {
            HttpRequestHelper helper = new HttpRequestHelper();
            InputStream is = helper.doRequest(url, params, method);

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
            in.close();

            JsonElement element = new JsonParser().parse(stringBuilder.toString());
            JsonObject object = element.getAsJsonObject();

            if (!object.get("success").getAsBoolean()){
                throw new PhaxioException(object.get("message").getAsString());
            }
            return object;
        }
        catch(PhaxioException e){
            throw e;
        }
        catch(Exception e){
            throw new PhaxioException("There was a problem contacting the service", e);
        }
    }

    public static InputStream doRequestForInputStream(String operation, Map<String, Object> params,
            String method) throws PhaxioException {
        
        params.put("api_key", apiKey);
        params.put("api_secret", apiSecret);

        String url = host + version + "/" + operation;

        try {
            HttpRequestHelper helper = new HttpRequestHelper();
            InputStream is = helper.doRequest(url, params, method);

            if (!helper.getContentType().equals("application/json")){
                return is;
            }
            else {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    stringBuilder.append(inputLine);
                in.close();

                JsonElement element = new JsonParser().parse(stringBuilder.toString());
                JsonObject object = element.getAsJsonObject();
                throw new PhaxioException(object.get("message").getAsString());
            }
        }
        catch(PhaxioException e){
            throw e;
        }
        catch(Exception e){
            throw new PhaxioException("There was a problem contacting the service", e);
        }
    }

    public static JsonObject getAccountStatus() throws PhaxioException {
        return doRequest("getAccountStatus", null, "POST");
    }


}
