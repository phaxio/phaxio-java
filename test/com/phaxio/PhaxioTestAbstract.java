package com.phaxio;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phaxio.Phaxio;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.junit.Before;

abstract public class PhaxioTestAbstract {

    @Before
    public void loadCredentials() throws Exception{
        FileInputStream fis = new FileInputStream(new File("./test/key.json"));
        Thread.sleep(1000);
        String line = "";
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        while ((line = br.readLine()) != null){
            builder.append(line);
        }

        JsonObject object = (new JsonParser()).parse(builder.toString()).getAsJsonObject();
        Phaxio.apiKey = object.get("key").getAsString();
        Phaxio.apiSecret = object.get("secret").getAsString();
    }
}
